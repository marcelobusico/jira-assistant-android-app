package ar.com.simbya.jiraassistant.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

import ar.com.simbya.jiraassistant.R;
import ar.com.simbya.jiraassistant.models.IssueListModel;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesLoader;
import ar.com.simbya.jiraassistant.serviceAdapters.JiraServiceAdapter;
import io.fabric.sdk.android.Fabric;

public class ResultActivity extends AppCompatActivity {

    private AppPreferencesLoader appPreferencesLoader;
    private JiraServiceAdapter jiraServiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Crashlytics
        Fabric.with(this, new Crashlytics());

        appPreferencesLoader = new AppPreferencesLoader(this);
        jiraServiceAdapter = new JiraServiceAdapter(appPreferencesLoader);

        jiraServiceAdapter.searchIssues(new JiraServiceAdapter.SearchIssuesCompletionHandler() {
            @Override
            public void onComplete(IssueListModel issueListModel) {
                displayMessage("Success!", issueListModel.toString());
            }

            @Override
            public void onError(Throwable error) {
                displayMessage("Error!", error.toString());
            }
        });
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
