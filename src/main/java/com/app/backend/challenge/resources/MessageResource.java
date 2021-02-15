package com.app.backend.challenge.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Message Model
 */
public class MessageResource {

    private int id = -1;
    private int sender = -1;
    private int recipient = -1;
    private long timestamp = -1;
    private ContentResource content = null;

    public int getId() {
        return id;
    }

    public int getSender() {
        return sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS");
        return dateFormat.format(new Date(timestamp));
    }

    public ContentResource getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setContent(ContentResource content) {
        this.content = content;
    }

}


