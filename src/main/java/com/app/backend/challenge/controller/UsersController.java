package com.app.backend.challenge.controller;

import com.app.backend.challenge.resources.UserResource;
import com.app.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class UsersController {

    public static Route createUser = (Request req, Response resp) -> {
        // TODO: Create a New User
        return JSONUtil.dataToJson(new UserResource());
    };
}
