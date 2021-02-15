package com.app.backend.challenge.dao;

import org.sqlite.SQLiteConfig;

/**
 * SQLite DB configuration
 */
public class DBConfig {

    public static final String DB_PATH = "./";
    public static final String DB_FILE_NAME = "database.db";
    public static final String DB_FILE = DB_PATH + DB_FILE_NAME;
    public static final String DB_URL = "jdbc:sqlite:" +  DB_FILE;
    public static SQLiteConfig config = new SQLiteConfig();

    static {
        config.enforceForeignKeys(true);
    }


}
