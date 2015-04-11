package com.wms.form.model;

public class SupplierSearchForm {
    
    private int currentPage;
    private int numPerPage;
    private String isDisable;
    private String column;
    private String value;
    
    public SupplierSearchForm(){
        
    }
    
    public SupplierSearchForm(int currentPage, String isDisable, String column, String value) {
        this.currentPage = currentPage;
        this.isDisable = isDisable;
        this.column = column;
        this.value = value;
    }
    
    public SupplierSearchForm(int numPerPage, int currentPage, String isDisable, String column, String value) {
        this.numPerPage = numPerPage;
        this.currentPage = currentPage;
        this.isDisable = isDisable;
        this.column = column;
        this.value = value;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getIsDisable() {
        return isDisable;
    }
    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }
    
    public String getColumn() {
        return column;
    }
    public void setColumn(String column) {
        this.column = column;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value.trim();
    }
    @Override
    public String toString() {
        return "SupplierSearchForm [pageNum=" 
                + currentPage + ", isDisable=" + isDisable + ", column="
                + column + ", value=" + value + "]";
    }
    
}
