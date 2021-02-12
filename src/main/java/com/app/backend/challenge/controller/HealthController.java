package com.app.backend.challenge.controller;

import com.app.backend.challenge.resources.HealthResource;
import com.app.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class HealthController {

    public static Route check = (Request req, Response rep) -> {
        // TODO: Check service health. Feel free to add any check you consider necessary
        return JSONUtil.dataToJson(new HealthResource());
    };
}
