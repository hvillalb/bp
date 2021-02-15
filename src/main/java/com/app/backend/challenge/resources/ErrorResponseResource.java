package com.app.backend.challenge.resources;

/**
 * Error Response Model
 */
public class ErrorResponseResource {
    private String error;

    public ErrorResponseResource(final String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
