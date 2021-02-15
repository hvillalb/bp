package com.app.backend.challenge.controller;

import com.app.backend.challenge.dao.DBApi;
import com.app.backend.challenge.resources.*;
import com.app.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

/**
 * Send and Read message API
 */
public class MessagesController {

    /**
     * Send message API entry point
     */
    public static Route sendMessage = (Request req, Response rep) -> {
        try {
            DBApi dbAPI = new DBApi();
            MessageResource  messageToBeAdded = (MessageResource) JSONUtil.jsonToData(req.body(), MessageResource.class);
            MessageIdResource messageIdStored = dbAPI.addMessage(messageToBeAdded);
            return JSONUtil.dataToJson(messageIdStored);
        } catch (Exception e) {
            rep.status(HTTP_INTERNAL_ERROR);
            return JSONUtil.dataToJson(new ErrorResponseResource(e.getMessage()));
        }
    };


    /**
     * Read messages API entry point
     */
    public static Route getMessages = (Request req, Response rep) -> {
        try {
            DBApi dbAPI = new DBApi();
            MessagesRequestResource messageReadCriteria =
                    (MessagesRequestResource) JSONUtil.jsonToData(req.body(), MessagesRequestResource.class);
            MessageListResource messageListResource = dbAPI.readMessages(messageReadCriteria);
            return JSONUtil.dataToJson(messageListResource);
        } catch (Exception e) {
            rep.status(HTTP_INTERNAL_ERROR);
            return JSONUtil.dataToJson(new ErrorResponseResource(e.getMessage()));
        }
    };

}
