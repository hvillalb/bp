package com.app.backend.challenge.controller;

import com.app.backend.challenge.dao.DBApi;
import com.app.backend.challenge.resources.ErrorResponseResource;
import com.app.backend.challenge.resources.LoginResource;
import com.app.backend.challenge.resources.UserResource;
import com.app.backend.challenge.utils.JSONUtil;
import com.app.backend.challenge.utils.TokenRegistry;
import spark.Request;
import spark.Response;
import spark.Route;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Login API
 */
public class AuthController {

    /**
     * Login API entry point
     */
    public static Route login = (Request req, Response resp) -> {
        try {
            // Get user credentials from request
            final UserResource userCredentials = (UserResource) JSONUtil.jsonToData(req.body(), UserResource.class);

            // Read user credentials from DB
            final DBApi dbApi = new DBApi();
            final UserResource user = dbApi.getUserByName(userCredentials.getUsername());

            // Validate credentials
            if (user.getUsername().equals(userCredentials.getUsername())
                    && user.getPassword().equals(userCredentials.getPassword())) {

                // Get a token
                String token = TokenRegistry.getInstance().getToken(user.getUsername());
                return JSONUtil.dataToJson(new LoginResource(user.getId(), token));
            } else {
                resp.status(HTTP_UNAUTHORIZED);
                return JSONUtil.dataToJson(new ErrorResponseResource("Invalid credentials"));
            }
        } catch (Exception e) {
            resp.status(HTTP_INTERNAL_ERROR);
            return JSONUtil.dataToJson(new ErrorResponseResource(e.getMessage()));
        }
    };
}
