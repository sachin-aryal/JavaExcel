package com.simple.excel.implementation;

import org.json.simple.JSONObject;

/**
 * Author: SACHIN
 * Date: 3/23/2016.
 */
public interface ExcelOperator {

    void processFile();

    JSONObject getFinalData();

    void generateExcel(boolean merged);

    void showProcessedData();

    void setMapping();

    void mergeColumns();

    void setFinalData(JSONObject data);
}
