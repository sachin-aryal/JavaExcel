package com.simple.excel.implementation;

import org.json.simple.JSONObject;

/**
 * Author: SACHIN
 * Date: 4/2/2016.
 */
public interface DatabaseOperator {

    void setDatabaseColumnMapping();

    JSONObject getDatabaseFinalData();

    void showProcessDatabaseData();
}
