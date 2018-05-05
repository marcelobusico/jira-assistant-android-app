package ar.com.simbya.jiraassistant.preferences;

import java.io.Serializable;

public class AppPreferencesModel implements Serializable {

    private String jiraServer;
    private String username;
    private String password;
    private int filterId;
    private int sprintCapacityPerPerson;

    public String getJiraServer() {
        return jiraServer;
    }

    public void setJiraServer(String jiraServer) {
        this.jiraServer = jiraServer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public int getSprintCapacityPerPerson() {
        return sprintCapacityPerPerson;
    }

    public void setSprintCapacityPerPerson(int sprintCapacityPerPerson) {
        this.sprintCapacityPerPerson = sprintCapacityPerPerson;
    }
}
