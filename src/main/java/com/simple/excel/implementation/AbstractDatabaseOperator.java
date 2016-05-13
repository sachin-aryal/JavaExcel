package com.simple.excel.implementation;

import com.simple.pozo.DatabaseField;

/**
 * Author: SACHIN
 * Date: 4/6/2016.
 */
public abstract class AbstractDatabaseOperator implements DatabaseOperator{

    protected static DatabaseField databaseField;

    public static DatabaseField getDatabaseField() {
        return databaseField;
    }

    public static void setDatabaseField(DatabaseField databaseField) {
        AbstractDatabaseOperator.databaseField = databaseField;
    }

    @Override
    public void setDatabaseColumnMapping(){};

    @Override
    public void saveData(){}

}
