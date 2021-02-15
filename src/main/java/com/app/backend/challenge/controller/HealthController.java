package com.app.backend.challenge.controller;

import com.app.backend.challenge.dao.DBConfig;
import com.app.backend.challenge.resources.HealthResource;
import com.app.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Health Check API
 */
public class HealthController {

    private static final String HEALTHY = "ok";
    private static final String UNHEALTHY = "no";
    private static final int NUMBER_OF_TABLES_TO_CHECK = 2;
    private static final String SQL_HEALTH_CHECK = "SELECT name FROM sqlite_master " +
            "WHERE type = 'table' AND (" +
            "name = 'users' " +
            "OR name = 'messages'" +
            ")";


    /**
     * Health Check API entry point
     */
    public static Route check = (Request req, Response rep) -> {
        return JSONUtil.dataToJson(new HealthResource(checkDB()));
    };


    /**
     * Basically verify the SQLite file exists and solution tables were created
     *
     * @return HEALTHY if it can connect to DB. Otherwise UNHEALTHY
     */
    public static String checkDB() {
        final File dbFile = new File(DBConfig.DB_FILE);
        if (!dbFile.exists()) {
            return UNHEALTHY;
        }
        try (Connection conn = DriverManager.getConnection(DBConfig.DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(SQL_HEALTH_CHECK)) {
            int numRecords = 0;
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numRecords++;
            }
            if (numRecords == NUMBER_OF_TABLES_TO_CHECK) {
                return HEALTHY;
            }
            return UNHEALTHY;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return UNHEALTHY;
    }


}
