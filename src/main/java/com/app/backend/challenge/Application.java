package com.app.backend.challenge;

import com.app.backend.challenge.controller.HealthController;
import com.app.backend.challenge.controller.MessagesController;
import com.app.backend.challenge.controller.UsersController;
import com.app.backend.challenge.filter.TokenValidatorFilter;
import com.app.backend.challenge.dao.DBUtil;
import com.app.backend.challenge.utils.Path;
import com.app.backend.challenge.controller.AuthController;
import spark.Spark;

public class Application {

    public static void main(String[] args) {

        // Spark Configuration
        Spark.port(8080);

        // Configure Endpoints
        // Users
        Spark.post(Path.USERS, UsersController.createUser);
        // Auth
        Spark.post(Path.LOGIN, AuthController.login);
        // Messages
        Spark.before(Path.MESSAGES, TokenValidatorFilter.validateUser);
        Spark.post(Path.MESSAGES, MessagesController.sendMessage);
        Spark.get(Path.MESSAGES, MessagesController.getMessages);
        // Health
        Spark.post(Path.HEALTH, HealthController.check);

        DBUtil.createDataBase();
        DBUtil.createSchema();

    }

}
