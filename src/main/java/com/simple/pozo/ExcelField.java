package com.simple.pozo;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Author: SACHIN
 * Date: 3/23/2016.
 */
public class ExcelField {
    private File file[];
    private Map<String,String> columnMaps;
    List<String> mergedColumns;

    private int totalColumn;

    public List<String> getMergedColumns() {
        return mergedColumns;
    }

    public void setMergedColumns(List<String> mergedColumns) {
        this.mergedColumns = mergedColumns;
    }

    public Map<String, String> getColumnMaps() {
        return columnMaps;
    }

    public void setColumnMaps(Map<String, String> columnMaps) {
        this.columnMaps = columnMaps;
    }


    public int getTotalColumn() {
        return totalColumn;
    }

    public void setTotalColumn(int totalColumn) {
        this.totalColumn = totalColumn;
    }


    public File[] getFile() {
        return file;
    }

    public void setFile(File[] file) {
        this.file = file;
    }


    public ExcelField(File[] file, Map<String,String> columnMaps, int totalColumn) {
        this.file = file;
        this.totalColumn=totalColumn;
        this.columnMaps=columnMaps;
    }

    public ExcelField(){

    }
}
