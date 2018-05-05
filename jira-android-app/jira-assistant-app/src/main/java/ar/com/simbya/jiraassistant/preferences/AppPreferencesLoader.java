package ar.com.simbya.jiraassistant.preferences;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppPreferencesLoader extends AppPreferencesStorage {

    private static final String PREFERENCE_APP = "PREFERENCE_APP";

    public AppPreferencesLoader(Activity activityContext) {
        super(activityContext);
    }

    public void saveAppPreferences(AppPreferencesModel appPreferencesModel) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        if (appPreferencesModel == null) {
            editor.remove(PREFERENCE_APP);
        } else {
            Gson gson = new Gson();
            String jsonString = gson.toJson(appPreferencesModel);
            editor.putString(PREFERENCE_APP, jsonString);
        }

        editor.apply();
    }

    public AppPreferencesModel loadAppPreferences() {
        SharedPreferences preferences = getSharedPreferences();

        String appPreferencesJsonString = preferences.getString(PREFERENCE_APP, null);

        if (appPreferencesJsonString == null) {
            return null;
        } else {
            Gson gson = new Gson();
            AppPreferencesModel appPreferencesModel = gson.fromJson(appPreferencesJsonString, AppPreferencesModel.class);
            return appPreferencesModel;
        }
    }
}
