package com.simple.pozo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: SACHIN
 * Date: 4/2/2016.
 */
public class DatabaseField {

    Map<String,String> databaseColumnMap = new LinkedHashMap<>();

    public DatabaseField(Map<String, String> databaseColumnMap) {
        this.databaseColumnMap = databaseColumnMap;
    }

    public Map<String, String> getDatabaseColumnMap() {
        return databaseColumnMap;
    }

    public void setDatabaseColumnMap(Map<String, String> databaseColumnMap) {
        this.databaseColumnMap = databaseColumnMap;
    }
}
