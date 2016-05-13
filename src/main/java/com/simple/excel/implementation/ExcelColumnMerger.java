package com.simple.excel.implementation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: SACHIN
 * Date: 4/2/2016.
 */
public class ExcelColumnMerger extends AbstractExcelOperator{

    private String columnsToBeMerged;

    public ExcelColumnMerger(String columnsToBeMerged){
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
//                    System.out.println("Writing space");
                }
            }
            mergedColumns.add(mergedCellString.toString());
            String cellName = allColumns.get(Integer.parseInt(cols[0]));
            for (String eachCols : cols) {
                allColumns.remove(Integer.parseInt(eachCols));
                allColumns.add(Integer.parseInt(eachCols),"");
            }
            if(mergeData.keySet().contains(cellName)){
                mergeData.put(cellName+"-"+headIndex, allData[headIndex]);
            }else{
                mergeData.put(cellName, allData[headIndex]);
            }
            headIndex++;
        }
        fullData.put("mergeData",mergeData);

        int dupIndex=0;
        List<String> all = allColumns.stream().collect(Collectors.toList());
        for (String cellName : all) {
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
                }
                if(initialData.keySet().contains(cellName)){
                    initialData.put(cellName+"-"+dupIndex, allData[headIndex]);
                }else{
                    initialData.put(cellName, allData[headIndex]);
                }
                dupIndex++;
                usedColumns.add(index);
                allColumns.remove(index);
                allColumns.add(index,"");
            }

        }
        fullData.put("unMergedData",initialData);
        excelOperator.setFinalData(fullData);
        excelField.setMergedColumns(mergedColumns);
        excelField.setMerged(true);
    }
}
