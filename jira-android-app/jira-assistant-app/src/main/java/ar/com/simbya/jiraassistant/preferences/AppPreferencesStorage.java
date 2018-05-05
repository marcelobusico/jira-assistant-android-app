package ar.com.simbya.jiraassistant.preferences;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public abstract class AppPreferencesStorage {

    private static final String PREFERENCES_NAME = "JIRA_PREFERENCES";
    protected final Activity activityContext;

    public AppPreferencesStorage(Activity activityContext) {
        this.activityContext = activityContext;
    }

    protected SharedPreferences getSharedPreferences() {
        return activityContext.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
    }
}
