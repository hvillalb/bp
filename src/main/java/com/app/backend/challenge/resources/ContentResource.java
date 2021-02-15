package com.app.backend.challenge.resources;

/**
 * Message Content Model
 */
public class ContentResource {

    private String text = null;
    private String type = null;

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

}
