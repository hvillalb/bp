package com.app.backend.challenge.controller;

import com.app.backend.challenge.dao.DBApi;
import com.app.backend.challenge.resources.ErrorResponseResource;
import com.app.backend.challenge.resources.UserResource;
import com.app.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

/**
 * Create User API
 */
public class UsersController {

    /**
     * Create User API entry point
     */
    public static Route createUser = (Request req, Response resp) -> {
        try {
            final DBApi dbApi = new DBApi();
            final UserResource userToBeCreated = (UserResource) JSONUtil.jsonToData(req.body(), UserResource.class);
            final UserResource userCreated = dbApi.addUser(userToBeCreated);
            return JSONUtil.dataToJson(userCreated);
        } catch (Exception e) {
            resp.status(HTTP_INTERNAL_ERROR);
            return JSONUtil.dataToJson(new ErrorResponseResource(e.getMessage()));
        }
    };
}
