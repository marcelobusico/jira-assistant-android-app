package ar.com.simbya.jiraassistant.serviceAdapters;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import ar.com.simbya.jiraassistant.apiServices.JiraSearchApi;
import ar.com.simbya.jiraassistant.models.IssueListModel;
import ar.com.simbya.jiraassistant.models.IssueModel;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesLoader;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JiraServiceAdapter {

    private static final int MAX_RESULTS = 10000;

    @NonNull
    private final AppPreferencesLoader appPreferencesLoader;

    public JiraServiceAdapter(@NonNull AppPreferencesLoader appPreferencesLoader) {
        this.appPreferencesLoader = appPreferencesLoader;
    }

    public void searchIssues(final boolean includeSubtasks,
                             @NonNull final SearchIssuesCompletionHandler completionHandler) {

        try {
            AppPreferencesModel appPreferences = appPreferencesLoader.loadAppPreferences();

            Retrofit retrofit = getRetrofit(appPreferences);
            final JiraSearchApi jiraSearchApi = retrofit.create(JiraSearchApi.class);

            String jql = "filter=" + appPreferences.getFilterId();

            jiraSearchApi.searchIssues(jql, MAX_RESULTS).enqueue(
                    new Callback<IssueListModel>() {

                        @Override
                        public void onResponse(@NonNull Call<IssueListModel> call,
                                               @NonNull Response<IssueListModel> response) {

                            if (response.code() == 200) {
                                final IssueListModel issueListModel = response.body();

                                if (includeSubtasks) {
                                    AsyncTask.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                populateIssuesSubtasks(issueListModel, jiraSearchApi);

                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        completionHandler.onComplete(issueListModel);
                                                    }
                                                });
                                            } catch (final Exception ex) {
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        completionHandler.onError(ex);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    completionHandler.onComplete(issueListModel);
                                }
                            } else {
                                completionHandler.onError(new IllegalStateException(response.message()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<IssueListModel> call, @NonNull Throwable t) {
                            completionHandler.onError(t);
                        }
                    });
        } catch (Exception ex) {
            completionHandler.onError(ex);
        }
    }

    private void populateIssuesSubtasks(IssueListModel parentIssueModelList, @NonNull JiraSearchApi jiraSearchApi) {
        if (parentIssueModelList == null || parentIssueModelList.getIssues() == null) {
            return;
        }

        StringBuilder issueNameStringBuilder = new StringBuilder();
        for (IssueModel issueModel : parentIssueModelList.getIssues()) {
            if (issueNameStringBuilder.length() > 0) {
                issueNameStringBuilder.append(",");
            }
            issueNameStringBuilder.append(issueModel.getKey());
        }

        try {
            String jql = "parent in (" + issueNameStringBuilder + ")";
            Response<IssueListModel> response = jiraSearchApi.searchIssues(jql, MAX_RESULTS).execute();
            IssueListModel completeSubTasksIssueListModel = response.body();
            if (response.code() == 200 && completeSubTasksIssueListModel != null) {

                Map<String, IssueModel> completeSubTaskIssuesById = new HashMap<>();
                for (IssueModel completeSubTaskIssue : completeSubTasksIssueListModel.getIssues()) {
                    completeSubTaskIssuesById.put(completeSubTaskIssue.getKey(), completeSubTaskIssue);
                }

                for (IssueModel parentIssue : parentIssueModelList.getIssues()) {
                    for (IssueModel subtaskIssue : parentIssue.getFields().getSubtasks()) {
                        IssueModel completeSubtaskIssue = completeSubTaskIssuesById.get(subtaskIssue.getKey());
                        subtaskIssue.setFields(completeSubtaskIssue.getFields());
                    }
                }
            } else {
                throw new IllegalStateException(
                        "Error code " + response.code() + " retrieving issues sub-tasks");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Cannot retrieve issues sub-tasks", e);
        }
    }

    @NonNull
    private Retrofit getRetrofit(@NonNull AppPreferencesModel appPreferences) {
        String jiraServer = appPreferences.getJiraServer();
        if (!jiraServer.endsWith("/")) {
            jiraServer = jiraServer + "/";
        }

        return new Retrofit.Builder()
                .baseUrl(jiraServer)
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientFactory.getHttpClient(appPreferences))
                .build();
    }

    public interface SearchIssuesCompletionHandler {

        void onComplete(IssueListModel issueListModel);

        void onError(Throwable error);
    }
}
