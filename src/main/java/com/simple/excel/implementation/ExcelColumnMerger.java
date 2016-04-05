package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author: SACHIN
 * Date: 4/2/2016.
 */
public class ExcelColumnMerger extends AbstractExcelOperator{

    private ExcelOperator excelOperator;
    private ExcelField excelField;
    private String columnsToBeMerged;

    public ExcelColumnMerger(ExcelOperator excelOperator,ExcelField excelField,String columnsToBeMerged){
        this.excelOperator=excelOperator;
        this.excelField=excelField;
        this.columnsToBeMerged=columnsToBeMerged.trim();
    }

    @Override
    public void mergeColumns() {

        List<String> mergedColumns = new LinkedList<>();
        List<String> allColumns = new LinkedList<>();
        List<String> mergedColumnsIndex = new LinkedList<>();
        Map<Integer, String> colWithFile = new LinkedHashMap<>();
        int index = 0;
        for (File f : excelField.getFile()) {
            for (String cell : excelField.getColumnMaps().keySet()) {
                colWithFile.put(index++, f.getName());
                allColumns.add(cell);
            }
        }

        String[] multiColumns = columnsToBeMerged.split(",");
        for (String mulCol : multiColumns) {
            mergedColumnsIndex.add(mulCol);
        }

        JSONObject mergeData = new JSONObject();
        JSONObject initialData = new JSONObject();
        JSONArray allData[] = new JSONArray[allColumns.size()];

        JSONObject fullData = new JSONObject();
        JSONObject data = excelOperator.getFinalData();
        JSONObject finalData = (JSONObject) data.get("data");

        List<Integer> usedColumns = new LinkedList<>();

        int headIndex = 0;
        StringBuilder mergedCellString;
        for (String mColumns : mergedColumnsIndex) {
            allData[headIndex] = new JSONArray();
            mergedCellString = new StringBuilder();
            String cols[] = mColumns.split("-");
            for (String eachCols : cols) {
                index = Integer.parseInt(eachCols);
                String fileName = colWithFile.get(index);
                String cellName = allColumns.get(index);
                mergedCellString.append(cellName).append("-");
                usedColumns.add(index);
                JSONObject fileJson = (JSONObject) finalData.get(fileName);
                JSONArray allCellData = (JSONArray) fileJson.get(cellName);
                try {
                    for (int i = 0; i < allCellData.size(); i++) {
                        allData[headIndex].add(allCellData.get(i));
                    }

                } catch (Exception ex) {
                    allData[headIndex].add("");
                    System.out.println("Writing space");
                }
            }
            mergedColumns.add(mergedCellString.toString());
            String cellName = allColumns.get(Integer.parseInt(cols[0]));
            for (String eachCols : cols) {
                allColumns.remove(Integer.parseInt(eachCols));
                allColumns.add(Integer.parseInt(eachCols),"");
            }
            mergeData.put(cellName, allData[headIndex]);
            headIndex++;
        }
        fullData.put("mergeData",mergeData);

        for (String cellName : allColumns) {
            index = allColumns.indexOf(cellName);
            if (!usedColumns.contains(index)) {
                allData[headIndex] = new JSONArray();
                String fileName = colWithFile.get(index);
                JSONObject fileJson = (JSONObject) finalData.get(fileName);
                try {
                    JSONArray allCellData = (JSONArray) fileJson.get(cellName);
                    for (int i = 0; i < allCellData.size(); i++) {
                        allData[headIndex].add(allCellData.get(i));
                    }
                } catch (Exception ex) {
                    allData[headIndex].add("");
                    System.out.println("Writing space");
                }
                initialData.put(cellName, allData[headIndex]);
            }
        }
        fullData.put("unMergedData",initialData);
        excelOperator.setFinalData(fullData);
        excelField.setMergedColumns(mergedColumns);
    }
}
