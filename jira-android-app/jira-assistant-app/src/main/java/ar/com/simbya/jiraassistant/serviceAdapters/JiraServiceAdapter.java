package ar.com.simbya.jiraassistant.serviceAdapters;

import android.support.annotation.NonNull;
import android.util.Base64;

import ar.com.simbya.jiraassistant.apiServices.JiraSearchApi;
import ar.com.simbya.jiraassistant.models.IssueListModel;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesLoader;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JiraServiceAdapter {

    @NonNull
    private final AppPreferencesLoader appPreferencesLoader;

    public JiraServiceAdapter(@NonNull AppPreferencesLoader appPreferencesLoader) {
        this.appPreferencesLoader = appPreferencesLoader;
    }

    public void searchIssues(@NonNull final SearchIssuesCompletionHandler completionHandler) {
        try {
            AppPreferencesModel appPreferences = appPreferencesLoader.loadAppPreferences();

            Retrofit retrofit = getRetrofit(appPreferences);
            final JiraSearchApi jiraSearchApi = retrofit.create(JiraSearchApi.class);

            String jql = "filter=" + appPreferences.getFilterId();

            jiraSearchApi.searchIssues(jql).enqueue(
                    new Callback<IssueListModel>() {

                        @Override
                        public void onResponse(Call<IssueListModel> call, Response<IssueListModel> response) {
                            if (response.code() == 200) {
                                completionHandler.onComplete(response.body());
                            } else {
                                completionHandler.onError(new IllegalStateException(response.message()));
                            }
                        }

                        @Override
                        public void onFailure(Call<IssueListModel> call, Throwable t) {
                            completionHandler.onError(t);
                        }
                    });
        } catch (Exception ex) {
            completionHandler.onError(ex);
        }
    }

    @NonNull
    private Retrofit getRetrofit(@NonNull AppPreferencesModel appPreferences) {
        String jiraServer = appPreferences.getJiraServer();
        if (!jiraServer.endsWith("/")) {
            jiraServer = jiraServer + "/";
        }

        return new Retrofit.Builder()
                .baseUrl(jiraServer)
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientFactory.getHttpClient(appPreferences))
                .build();
    }

    public interface SearchIssuesCompletionHandler {

        void onComplete(IssueListModel issueListModel);

        void onError(Throwable error);
    }
}
