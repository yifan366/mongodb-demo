package com.zhongtai.commontypes;

/**
 * Created by rain on 2016/12/13.
 */
public class QueryOptions {
    private int PageSize = 0;
    private int PageIndex = 0;
    private String SortField = "";
    private boolean isASC = true;
    private String[] ReturnFields = null;

    public QueryOptions() {
    }

    public QueryOptions(int pageSize, int pageIndex, String sortField, boolean isASC, String[] returnFields) {
        this.PageSize = pageSize;
        this.PageIndex = pageIndex;
        this.SortField = sortField;
        this.isASC = isASC;
        this.ReturnFields = returnFields;
    }

    public int getPageSize() {
        return this.PageSize;
    }

    public void setPageSize(int pageSize) {
        this.PageSize = pageSize;
    }

    public int getPageIndex() {
        return this.PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.PageIndex = pageIndex;
    }

    public String getSortField() {
        return this.SortField;
    }

    public void setSortField(String sortField) {
        this.SortField = sortField;
    }

    public String[] getReturnFields() {
        return this.ReturnFields;
    }

    public void setReturnFields(String[] returnFields) {
        this.ReturnFields = returnFields;
    }

    public boolean isASC() {
        return this.isASC;
    }

    public void setASC(boolean isASC) {
        this.isASC = isASC;
    }
}
