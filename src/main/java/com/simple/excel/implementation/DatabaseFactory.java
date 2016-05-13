package com.simple.excel.implementation;

/**
 * Author: SACHIN
 * Date: 4/8/2016.
 */
public class DatabaseFactory {
    public static DatabaseOperator getObjectInstance(String className){
        try {
            Class cls = Class.forName("com.simple.excel.implementation."+className);
            return (DatabaseOperator) cls.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
