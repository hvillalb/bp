package com.app.backend.challenge.dao;

import java.io.File;
import java.sql.*;

/**
 * SQLite DB facilities
 */
public class DBUtil {

    /**
     * Create DB
     */
    public static void createDataBase() {
        final File dbFile = new File(DBConfig.DB_FILE);
        if(dbFile.exists()) {
            return;
        }
        try (Connection conn = DriverManager.getConnection(DBConfig.DB_URL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Create DB Schema entry point
     */
    public static void createSchema() {
        createUsersTable();
        createMessagesTable();
        createMessagesIndex();

    }


    /**
     * Create messages index
     */
    private static void createMessagesIndex() {
        final String userSql = "CREATE INDEX IF NOT EXISTS messages_idx1 ON messages (recipient, id)";
        try (Connection conn = DriverManager.getConnection(DBConfig.DB_URL, DBConfig.config.toProperties());
             Statement stmt = conn.createStatement()) {
            stmt.execute(userSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Create messages table schema
     */
    private static void createMessagesTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS messages ("
                + " id INTEGER PRIMARY KEY,"
                + "	sender STRING NOT NULL,"
                + "	recipient STRING NOT NULL,"
                + "	type STRING NOT NULL,"
                + "	text STRING NOT NULL,"
                + " timestamp INT NOT NULL, "
                + " CONSTRAINT fk1 FOREIGN KEY (sender) REFERENCES users(id), "
                + " CONSTRAINT fk2 FOREIGN KEY (recipient) REFERENCES users(id)"
                + ");";
        try (Connection conn = DriverManager.getConnection(DBConfig.DB_URL, DBConfig.config.toProperties());
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Create users table schema
     */
    private static void createUsersTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS users ("
                + " id INTEGER PRIMARY KEY,"
                + "	username STRING UNIQUE NOT NULL,"
                + "	password STRING NOT NULL"
                + ");";
        try (Connection conn = DriverManager.getConnection(DBConfig.DB_URL, DBConfig.config.toProperties());
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
