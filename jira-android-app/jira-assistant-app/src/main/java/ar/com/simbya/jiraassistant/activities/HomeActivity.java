package ar.com.simbya.jiraassistant.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.rengwuxian.materialedittext.MaterialEditText;

import ar.com.simbya.jiraassistant.R;
import ar.com.simbya.jiraassistant.features.sprintplanning.SprintPlanningActivity;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesLoader;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesModel;
import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity {

    private MaterialEditText jiraServerEditText;
    private MaterialEditText usernameEditText;
    private MaterialEditText passwordEditText;
    private MaterialEditText filterIdEditText;
    private MaterialEditText sprintCapacityPerPersonEditText;

    private AppPreferencesLoader appPreferencesLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Crashlytics
        Fabric.with(this, new Crashlytics());

        appPreferencesLoader = new AppPreferencesLoader(this);

        jiraServerEditText = findViewById(R.id.jiraServerEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        filterIdEditText = findViewById(R.id.filterIdEditText);
        sprintCapacityPerPersonEditText = findViewById(R.id.sprintCapacityPerPersonEditText);

        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueOptionSelected();
            }
        });

        AppPreferencesModel appPreferences = appPreferencesLoader.loadAppPreferences();

        if (appPreferences != null) {
            jiraServerEditText.setText(appPreferences.getJiraServer());
            usernameEditText.setText(appPreferences.getUsername());
            passwordEditText.setText(appPreferences.getPassword());
            filterIdEditText.setText("" + appPreferences.getFilterId());
            sprintCapacityPerPersonEditText.setText("" + appPreferences.getSprintCapacityPerPerson());
        }
    }

    private void continueOptionSelected() {
        try {
            AppPreferencesModel appPreferencesModel = new AppPreferencesModel();

            appPreferencesModel.setJiraServer(jiraServerEditText.getText().toString());
            appPreferencesModel.setUsername(usernameEditText.getText().toString());
            appPreferencesModel.setPassword(passwordEditText.getText().toString());

            appPreferencesModel.setFilterId(Integer.valueOf((filterIdEditText.getText().toString())));
            appPreferencesModel.setSprintCapacityPerPerson(Integer.valueOf(sprintCapacityPerPersonEditText.getText().toString()));

            appPreferencesLoader.saveAppPreferences(appPreferencesModel);

            Intent intent = new Intent(this, SprintPlanningActivity.class);
            startActivity(intent);
        } catch (Exception ex) {
            displayMessage("Error", "Please check all entered data and try again.");
        }
    }

    private void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
}
