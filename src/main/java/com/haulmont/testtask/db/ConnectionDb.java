package com.haulmont.testtask.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connecting to the database
 * @author Kabanov Andrey
 */
public  class ConnectionDb {
    private static Connection connnection = null;
    private static Properties properties = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;
    private static String classDriver = null;

    /**
     * connects to the database
     */
    private static void openConnection(){
        try(InputStream in = ConnectionDb.class.getClassLoader().getResourceAsStream("hsqldb.properties") ){
            properties = new Properties();
            properties.load(in);
            classDriver = properties.getProperty("class");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(classDriver);
            connnection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ConnectionDb.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static Connection getConnection(){
        if (connnection == null){
                openConnection();
        }
        return connnection;
    }
}
