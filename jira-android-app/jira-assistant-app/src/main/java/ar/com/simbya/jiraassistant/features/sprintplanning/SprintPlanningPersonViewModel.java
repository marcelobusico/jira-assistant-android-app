package ar.com.simbya.jiraassistant.features.sprintplanning;

public class SprintPlanningPersonViewModel {

    private String personImageUrl;
    private String personName;
    private int currentlyAssignedTasks;
    private int totalAssignedTime;
    private int totalWorkedTime;
    private int remainingAssignedTasks;
    private int remainingCapacity;

    public String getPersonImageUrl() {
        return personImageUrl;
    }

    public void setPersonImageUrl(String personImageUrl) {
        this.personImageUrl = personImageUrl;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getCurrentlyAssignedTasks() {
        return currentlyAssignedTasks;
    }

    public void setCurrentlyAssignedTasks(int currentlyAssignedTasks) {
        this.currentlyAssignedTasks = currentlyAssignedTasks;
    }

    public int getTotalAssignedTime() {
        return totalAssignedTime;
    }

    public void setTotalAssignedTime(int totalAssignedTime) {
        this.totalAssignedTime = totalAssignedTime;
    }

    public int getTotalWorkedTime() {
        return totalWorkedTime;
    }

    public void setTotalWorkedTime(int totalWorkedTime) {
        this.totalWorkedTime = totalWorkedTime;
    }

    public int getRemainingAssignedTasks() {
        return remainingAssignedTasks;
    }

    public void setRemainingAssignedTasks(int remainingAssignedTasks) {
        this.remainingAssignedTasks = remainingAssignedTasks;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }
}
