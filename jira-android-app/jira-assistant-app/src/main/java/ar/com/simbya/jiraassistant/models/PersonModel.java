package ar.com.simbya.jiraassistant.models;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonModel that = (PersonModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
