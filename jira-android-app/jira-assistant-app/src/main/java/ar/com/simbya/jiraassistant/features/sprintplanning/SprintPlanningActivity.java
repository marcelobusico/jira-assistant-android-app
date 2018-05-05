package ar.com.simbya.jiraassistant.features.sprintplanning;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;

import java.lang.ref.WeakReference;

import ar.com.simbya.jiraassistant.R;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesLoader;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesModel;
import ar.com.simbya.jiraassistant.serviceAdapters.JiraServiceAdapter;
import ar.com.simbya.jiraassistant.serviceAdapters.PicassoFactory;
import io.fabric.sdk.android.Fabric;

public class SprintPlanningActivity extends AppCompatActivity implements SprintPlanningView {

    private ProgressBar loadingProgressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private SprintPlanningTableAdapter tableAdapter;
    private SprintPlanningPresenter presenter;
    private AppPreferencesLoader appPreferencesLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_planning);

        //Crashlytics
        Fabric.with(this, new Crashlytics());

        bindComponents();
        injectDependencies();
        configureRecyclerView();
        presenter.start();
    }

    private void bindComponents() {
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void injectDependencies() {
        appPreferencesLoader = new AppPreferencesLoader(this);
        JiraServiceAdapter jiraServiceAdapter = new JiraServiceAdapter(appPreferencesLoader);

        presenter = new SprintPlanningPresenter(
                new WeakReference<SprintPlanningView>(this),
                new WeakReference<Context>(this),
                appPreferencesLoader,
                jiraServiceAdapter
        );
    }

    private void configureRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh(true);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AppPreferencesModel appPreferences = appPreferencesLoader.loadAppPreferences();
        PicassoFactory picassoFactory = new PicassoFactory(this, appPreferences);

        tableAdapter = new SprintPlanningTableAdapter(presenter, picassoFactory);
        recyclerView.setAdapter(tableAdapter);
    }

    @Override
    public void showScreenLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void showTableLoading() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(true);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    @Override
    public void refreshIssuesList() {
        tableAdapter.notifyDataSetChanged();
    }
}
