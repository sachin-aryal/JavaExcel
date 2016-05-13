package com.simple.excel.implementation;

import com.simple.pozo.DatabaseField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Author: SACHIN
 * Date: 4/8/2016.
 */
public class DatabaseConfig {
    public static Connection connection;
    private Statement statement;
    private String driver = "com.mysql.jdbc.Driver";


    public void createConnection(DatabaseField database){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection("jdbc:mysql://"+database.getHost()+"/"+database.getDatabaseName(),
                    database.getUserName(),database.getPassword());
            System.out.println("Successfully Connected to Database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



}
