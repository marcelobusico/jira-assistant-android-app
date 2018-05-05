package ar.com.simbya.jiraassistant.models;

import java.io.Serializable;
import java.util.List;

public class IssueModel implements Serializable {

    String id;
    String self;
    String key;
    FieldModel fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FieldModel getFields() {
        return fields;
    }

    public void setFields(FieldModel fields) {
        this.fields = fields;
    }
}
