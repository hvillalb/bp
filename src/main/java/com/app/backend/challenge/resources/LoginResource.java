package com.app.backend.challenge.resources;

/**
 * Login Model
 */
public class LoginResource {

    private int id;
    private String token;

    public LoginResource(final int id, final String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
