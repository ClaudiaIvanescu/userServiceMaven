package com.itfactory.project.user_service.service;

import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DbManager {
    private static String dbFile = "users.db";

    public static void setDbFile(String newDbFile) {
        dbFile = newDbFile;
        System.out.println("Using a new SQL database file " + new File(dbFile).getAbsolutePath());
    }
    public static Connection getConnection()  throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        config.setDateStringFormat("yyyy-MM-dd HH:mm:ss");
        return DriverManager.getConnection("jdbc:sqlite:"+dbFile, config.toProperties());

    }
}
