package ar.com.simbya.jiraassistant.serviceAdapters;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ar.com.simbya.jiraassistant.apiServices.JiraSearchApi;
import ar.com.simbya.jiraassistant.models.IssueListModel;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesLoader;
import ar.com.simbya.jiraassistant.preferences.AppPreferencesModel;
import okhttp3.OkHttpClient;
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

            String userAndPassword = appPreferences.getUsername() + ":" + appPreferences.getPassword();
            String authorization = "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
            String jql = "filter=" + appPreferences.getFilterId();

            jiraSearchApi.searchIssues(authorization, jql).enqueue(
                    new Callback<IssueListModel>() {

                        @Override
                        public void onResponse(Call<IssueListModel> call, Response<IssueListModel> response) {
                            completionHandler.onComplete(response.body());
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
                .client(getUnsafeOkHttpClient())
                .build();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface SearchIssuesCompletionHandler {

        void onComplete(IssueListModel issueListModel);

        void onError(Throwable error);
    }
}
