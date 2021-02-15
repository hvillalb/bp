package com.app.backend.challenge.filter;

import com.app.backend.challenge.utils.TokenRegistry;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Token validator filter
 */
public class TokenValidatorFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Token validator filter entry point
     */
    public static Filter validateUser = (Request req, Response resp) -> {
        try {
            boolean isAuthorized = TokenRegistry.getInstance().verify(req.headers(AUTHORIZATION_HEADER));
            if (!isAuthorized) {
                Spark.halt(HTTP_UNAUTHORIZED, "Invalid token");
            }
        } catch (Exception e) {
            Spark.halt(HTTP_UNAUTHORIZED, "Unable to validate user credentials");
        }
    };
}
