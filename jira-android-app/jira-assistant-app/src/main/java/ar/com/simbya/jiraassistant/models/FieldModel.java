package ar.com.simbya.jiraassistant.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FieldModel implements Serializable {

    private PersonModel assignee;
    private List<IssueModel> subtasks;

    @SerializedName("timeoriginalestimate")
    private Integer originalEstimate;

    @SerializedName("timeestimate")
    private Integer remainingEstimate;

    @SerializedName("timespent")
    private Integer timeSpent;

    @SerializedName("aggregatetimeoriginalestimate")
    private Integer subtasksOriginalEstimate;

    @SerializedName("aggregatetimeestimate")
    private Integer subtasksRemainingEstimate;

    @SerializedName("aggregatetimespent")
    private Integer subtasksTimeSpent;

    public PersonModel getAssignee() {
        return assignee;
    }

    public void setAssignee(PersonModel assignee) {
        this.assignee = assignee;
    }

    public List<IssueModel> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<IssueModel> subtasks) {
        this.subtasks = subtasks;
    }

    public Integer getOriginalEstimate() {
        return originalEstimate;
    }

    public void setOriginalEstimate(Integer originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    public Integer getRemainingEstimate() {
        return remainingEstimate;
    }

    public void setRemainingEstimate(Integer remainingEstimate) {
        this.remainingEstimate = remainingEstimate;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Integer getSubtasksOriginalEstimate() {
        return subtasksOriginalEstimate;
    }

    public void setSubtasksOriginalEstimate(Integer subtasksOriginalEstimate) {
        this.subtasksOriginalEstimate = subtasksOriginalEstimate;
    }

    public Integer getSubtasksRemainingEstimate() {
        return subtasksRemainingEstimate;
    }

    public void setSubtasksRemainingEstimate(Integer subtasksRemainingEstimate) {
        this.subtasksRemainingEstimate = subtasksRemainingEstimate;
    }

    public Integer getSubtasksTimeSpent() {
        return subtasksTimeSpent;
    }

    public void setSubtasksTimeSpent(Integer subtasksTimeSpent) {
        this.subtasksTimeSpent = subtasksTimeSpent;
    }
}
