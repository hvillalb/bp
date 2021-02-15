package com.app.backend.challenge.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User Model
 */
public class UserResource {

    private int id = -1;
    private String username = null;
    private String password = null;

    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public void setUsername(final String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(final String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }


}
