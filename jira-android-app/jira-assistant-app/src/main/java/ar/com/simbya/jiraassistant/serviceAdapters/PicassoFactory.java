package ar.com.simbya.jiraassistant.serviceAdapters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import ar.com.simbya.jiraassistant.preferences.AppPreferencesModel;

public class PicassoFactory {

    @NonNull
    private final Context context;

    @NonNull
    private final AppPreferencesModel appPreferences;

    private Picasso picasso;

    public PicassoFactory(@NonNull Context context, @NonNull AppPreferencesModel appPreferences) {
        this.context = context;
        this.appPreferences = appPreferences;
    }

    public Picasso getPicasso() {
        if (picasso != null) {
            return picasso;
        }

        OkHttp3Downloader downloader = new OkHttp3Downloader(HttpClientFactory.getHttpClient(appPreferences));

        picasso = new Picasso.Builder(context)
                .downloader(downloader)
                .build();

        return picasso;
    }
}
