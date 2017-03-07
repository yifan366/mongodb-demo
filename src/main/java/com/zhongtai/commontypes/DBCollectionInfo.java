package com.zhongtai.commontypes;

/**
 * Created by rain on 2016/12/13.
 */
public class DBCollectionInfo {
    private String DBName = "";
    private String CollectionName = "";

    public DBCollectionInfo() {
    }

    public DBCollectionInfo(String dBName, String collectionName) {
        this.DBName = dBName;
        this.CollectionName = collectionName;
    }

    public String getDBName() {
        return this.DBName;
    }

    public void setDBName(String dBName) {
        this.DBName = dBName;
    }

    public String getCollectionName() {
        return this.CollectionName;
    }

    public void setCollectionName(String collectionName) {
        this.CollectionName = collectionName;
    }
}
