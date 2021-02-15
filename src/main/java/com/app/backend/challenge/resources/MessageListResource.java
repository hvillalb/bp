package com.app.backend.challenge.resources;

import java.util.List;

/**
 * Message List Model
 */
public class MessageListResource {

    private List<MessageResource> messages;

    public List<MessageResource> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResource> messages) {
        this.messages = messages;
    }


}
