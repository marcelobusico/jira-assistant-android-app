package ar.com.simbya.jiraassistant.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AvatarModel implements Serializable {

    @SerializedName("48x48")
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
