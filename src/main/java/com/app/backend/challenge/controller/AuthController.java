package com.app.backend.challenge.controller;

import com.app.backend.challenge.resources.LoginResource;
import com.app.backend.challenge.utils.JSONUtil;
import spark.*;

public class AuthController {

    public static Route login = (Request req, Response resp) -> {
        // TODO: Login and return a token
        return JSONUtil.dataToJson(new LoginResource());
    };

}
