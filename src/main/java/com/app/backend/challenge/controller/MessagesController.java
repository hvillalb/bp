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
            MessageResource messageToBeAdded = (MessageResource) JSONUtil.jsonToData(req.body(), MessageResource.class);
            assertMessageAndContent(messageToBeAdded);
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


    /**
     * Validate a message and content
     *
     * @param message
     */
    private static void assertMessageAndContent(final MessageResource message) throws Exception {
        ContentType contentType = ContentType.valueOf(message.getContent().getType().toUpperCase());
        switch (contentType) {
            case TEXT:
                assertTextContent(message.getContent());
                break;
            case IMAGE:
                assertImageContent(message.getContent());
                break;
            case VIDEO:
                assertVideoContent(message.getContent());
                break;
        }
    }

    /**
     * Validate video type content
     * @param content
     * @throws Exception
     */
    private static void assertVideoContent(final ContentResource content) throws Exception {
        if(content.getUrl() == null
                || content.getUrl().isEmpty()
                || content.getSource() == null
                || content.getSource().isEmpty()) {
            throw new Exception("video content type metadata is wrong");
        }

    }


    /**
     * Validate image type content
     * @param content
     * @throws Exception
     */
    private static void assertImageContent(final ContentResource content) throws Exception {
        if(content.getUrl() == null
                || content.getUrl().isEmpty()
                || content.getHeight() <= 0
                || content.getWidth() <= 0) {
            throw new Exception("image content type metadata is wrong");
        }
    }


    /**
     * Validate text type content
     * @param content
     * @throws Exception
     */
    private static void assertTextContent(final ContentResource content) throws Exception {
        if (content.getText() == null || content.getText().isEmpty()) {
            throw new Exception("text content type cannot be null or empty");
        }
    }

}
