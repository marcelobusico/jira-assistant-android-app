package ar.com.simbya.jiraassistant.models;

import java.io.Serializable;
import java.util.List;

public class IssueListModel implements Serializable {

    int startAt;
    int maxResults;
    int total;
    List<IssueModel> issues;

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<IssueModel> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueModel> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "IssueListModel{" +
                "startAt=" + startAt +
                ", maxResults=" + maxResults +
                ", total=" + total +
                '}';
    }
}
