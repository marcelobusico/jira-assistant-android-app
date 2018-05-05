package ar.com.simbya.jiraassistant.models;

import java.io.Serializable;

public class PersonModel implements Serializable {

    private String name;
    private String displayName;
    private AvatarModel avatarUrls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public AvatarModel getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(AvatarModel avatarUrls) {
        this.avatarUrls = avatarUrls;
    }
}
