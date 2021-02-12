package com.app.backend.challenge.controller;

import com.app.backend.challenge.resources.MessageResource;
import com.app.backend.challenge.utils.JSONUtil;
import java.util.Collections;
import spark.Request;
import spark.Response;
import spark.Route;

public class MessagesController {

    public static Route sendMessage = (Request req, Response rep) -> {
        // TODO: Send a New Message
        return JSONUtil.dataToJson(new MessageResource());
    };

    public static Route getMessages = (Request req, Response rep) -> {
        // TODO: Retrieve list of Messages
        return JSONUtil.dataToJson(Collections.singletonList(new MessageResource()));
    };

}
