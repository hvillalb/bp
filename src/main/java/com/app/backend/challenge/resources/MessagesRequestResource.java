package com.app.backend.challenge.resources;

/**
 * Message Request Model
 */
public class MessagesRequestResource {

    private int recipient;
    private int start;
    private int limit;

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    public int getRecipient() {
        return recipient;
    }

    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }

}
