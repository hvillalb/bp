package com.app.backend.challenge.filter;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

public class TokenValidatorFilter {

    public static Filter validateUser = (Request req, Response resp) -> {
        // TODO: validate token
        Spark.halt(401, "Invalid token");
    };
}
