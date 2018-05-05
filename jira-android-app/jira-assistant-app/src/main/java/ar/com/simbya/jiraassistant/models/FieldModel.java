package ar.com.simbya.jiraassistant.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FieldModel implements Serializable {

    private PersonModel assignee;

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
