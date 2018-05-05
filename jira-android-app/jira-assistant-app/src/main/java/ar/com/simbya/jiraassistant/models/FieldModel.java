package ar.com.simbya.jiraassistant.models;

import java.io.Serializable;

public class FieldModel implements Serializable {

    private ProgressModel progress;
    private ProgressModel aggregateprogress;
    private PersonModel assignee;

    public ProgressModel getProgress() {
        return progress;
    }

    public void setProgress(ProgressModel progress) {
        this.progress = progress;
    }

    public ProgressModel getAggregateprogress() {
        return aggregateprogress;
    }

    public void setAggregateprogress(ProgressModel aggregateprogress) {
        this.aggregateprogress = aggregateprogress;
    }

    public PersonModel getAssignee() {
        return assignee;
    }

    public void setAssignee(PersonModel assignee) {
        this.assignee = assignee;
    }
}
