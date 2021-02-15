package com.app.backend.challenge.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Message Id Model
 */
public class MessageIdResource {

    private int id = -1;
    private long timestamp = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS");
        return dateFormat.format(new Date(timestamp));
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}


