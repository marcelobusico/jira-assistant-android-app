package ar.com.simbya.jiraassistant.features.sprintplanning;

public interface SprintPlanningView {

    void showScreenLoading();

    void showTableLoading();

    void hideLoading();

    void displayMessage(String title, String message);

    void refreshIssuesList();
}
