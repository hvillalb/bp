package com.app.backend.challenge.resources;

/**
 * Health Check Model
 */
public class HealthResource {

    private String health;

    public HealthResource(final String health) {
        this.health = health;
    }

    public String getHealth() {
        return health;
    }
}
