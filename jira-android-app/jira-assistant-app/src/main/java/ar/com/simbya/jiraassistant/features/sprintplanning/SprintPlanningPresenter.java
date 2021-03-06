package ar.com.simbya.jiraassistant.features.sprintplanning;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.com.simbya.jiraassistant.R;
import ar.com.simbya.jiraassistant.models.IssueListModel;
import ar.com.simbya.jiraassistant.models.IssueModel;
import ar.com.simbya.jiraassistant.models.PersonModel;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesLoader;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesModel;
import ar.com.simbya.jiraassistant.serviceAdapters.JiraServiceAdapter;

public class SprintPlanningPresenter {

    private final WeakReference<SprintPlanningView> viewReference;
    private final WeakReference<Context> contextReference;
    private final JiraServiceAdapter serviceAdapter;
    private final AppPreferencesLoader appPreferencesLoader;
    private List<SprintPlanningPersonViewModel> viewModelList;
    private boolean isLoading = false;

    SprintPlanningPresenter(@NonNull WeakReference<SprintPlanningView> viewReference,
                            @NonNull WeakReference<Context> contextReference,
                            @NonNull AppPreferencesLoader appPreferencesLoader,
                            @NonNull JiraServiceAdapter serviceAdapter) {

        this.viewReference = viewReference;
        this.contextReference = contextReference;
        this.appPreferencesLoader = appPreferencesLoader;
        this.serviceAdapter = serviceAdapter;
    }

    public void start() {
        if (numberOfElements() == 0 && !isLoading) {
            searchIssuesAsync(true);
        }
    }

    public void refresh(boolean screenLoading) {
        searchIssuesAsync(screenLoading);
    }

    public int numberOfElements() {
        if (viewModelList == null) {
            return 0;
        }

        return viewModelList.size();
    }

    public SprintPlanningPersonViewModel elementViewModel(int position) {
        if (viewModelList == null) {
            return null;
        }

        if (position >= viewModelList.size()) {
            return null;
        }

        return viewModelList.get(position);
    }

    private void searchIssuesAsync(boolean screenLoading) {
        if (isLoading) {
            return;
        }

        isLoading = true;

        SprintPlanningView view = viewReference.get();
        if (view != null) {
            if (screenLoading) {
                view.showScreenLoading();
            } else {
                view.showTableLoading();
            }
        }

        serviceAdapter.searchIssues(true, new JiraServiceAdapter.SearchIssuesCompletionHandler() {

            @Override
            public void onComplete(IssueListModel issueListModel) {
                generateViewModelList(issueListModel);
                SprintPlanningView view = viewReference.get();
                Context context = contextReference.get();
                if (view != null && context != null) {
                    view.hideLoading();
                    view.refreshIssuesList();
                }
                isLoading = false;
            }

            @Override
            public void onError(Throwable error) {
                generateViewModelList(null);
                SprintPlanningView view = viewReference.get();
                Context context = contextReference.get();
                if (view != null && context != null) {
                    view.hideLoading();
                    view.displayMessage(
                            context.getString(R.string.message_title_error),
                            context.getString(R.string.message_load_issues_error) + "\n\n" + error.getMessage());
                }
                isLoading = false;
            }
        });
    }

    private void generateViewModelList(IssueListModel issueListModel) {
        viewModelList = new LinkedList<>();
        if (issueListModel == null || issueListModel.getIssues() == null) {
            return;
        }

        AppPreferencesModel appPreferences = appPreferencesLoader.loadAppPreferences();
        int sprintCapacityPerPerson = appPreferences.getSprintCapacityPerPerson();

        //Group issues by person
        Map<PersonModel, List<IssueModel>> issuesByPerson = new HashMap<>();
        for (IssueModel issueModel : issueListModel.getIssues()) {
            addIssueByPerson(issuesByPerson, issueModel);
            for (IssueModel subTaskIssueModel : issueModel.getFields().getSubtasks()) {
                addIssueByPerson(issuesByPerson, subTaskIssueModel);
            }
        }

        for (Map.Entry<PersonModel, List<IssueModel>> personAndIssue : issuesByPerson.entrySet()) {
            SprintPlanningPersonViewModel viewModel = new SprintPlanningPersonViewModel();
            PersonModel person = personAndIssue.getKey();
            List<IssueModel> issues = personAndIssue.getValue();

            if (person == null) {
                viewModel.setPersonName("Unassigned");
            } else {
                viewModel.setPersonImageUrl(person.getAvatarUrls().getAvatarUrl());
                viewModel.setPersonName(person.getDisplayName());
            }

            viewModel.setCurrentlyAssignedTasks(issues.size());

            int totalSeconds = 0;
            int progressSeconds = 0;
            int remainingSeconds = 0;

            for (IssueModel issue : issues) {
                if (issue.getFields().getOriginalEstimate() != null) {
                    totalSeconds += issue.getFields().getOriginalEstimate();
                }

                if (issue.getFields().getTimeSpent() != null) {
                    progressSeconds += issue.getFields().getTimeSpent();
                }

                if (issue.getFields().getRemainingEstimate() != null) {
                    remainingSeconds += issue.getFields().getRemainingEstimate();
                }
            }

            viewModel.setTotalAssignedTime(totalSeconds / 3600);
            viewModel.setTotalWorkedTime(progressSeconds / 3600);
            viewModel.setRemainingAssignedTasks(remainingSeconds / 3600);
            viewModel.setRemainingCapacity(sprintCapacityPerPerson - (remainingSeconds / 3600));

            viewModelList.add(viewModel);
        }
    }

    private void addIssueByPerson(Map<PersonModel, List<IssueModel>> issuesByPerson, IssueModel issueModel) {
        List<IssueModel> issueModelList = issuesByPerson.get(issueModel.getFields().getAssignee());
        if (issueModelList == null) {
            issueModelList = new LinkedList<>();
        }
        issueModelList.add(issueModel);
        issuesByPerson.put(issueModel.getFields().getAssignee(), issueModelList);
    }
}
