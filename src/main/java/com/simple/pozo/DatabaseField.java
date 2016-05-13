package com.simple.pozo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: SACHIN
 * Date: 4/2/2016.
 */
public class DatabaseField {

    private String host;
    private String databaseName;
    private String userName;
    private String password;
    private String tableName;

    Map<String,String> databaseColumnMap = new LinkedHashMap<>();

    Map<String,String> databaseColumnType = new LinkedHashMap<>();

    public DatabaseField(String host, String databaseName, String userName, String password, String tableName) {
        this.host = host;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.tableName = tableName;
    }

    public DatabaseField(){}

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public Map<String, String> getDatabaseColumnType() {
        return databaseColumnType;
    }

    public void setDatabaseColumnType(Map<String, String> databaseColumnType) {
        this.databaseColumnType = databaseColumnType;
    }

    public Map<String, String> getDatabaseColumnMap() {
        return databaseColumnMap;
    }

    public void setDatabaseColumnMap(Map<String, String> databaseColumnMap) {
        this.databaseColumnMap = databaseColumnMap;
    }
}
