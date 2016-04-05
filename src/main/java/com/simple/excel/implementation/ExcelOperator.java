package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.json.simple.JSONObject;

/**
 * Author: SACHIN
 * Date: 3/23/2016.
 */
public interface ExcelOperator {

    void processFile(ExcelField excelField);

    JSONObject getFinalData();

    void generateExcel(ExcelField excelField, ExcelOperator excelOperator,boolean merged);

    void showProcessedData();

    void setMapping();

    void mergeColumns();

    void setFinalData(JSONObject data);
}
