package com.zhongtai.commontypes;

/**
 * Created by rain on 2016/12/13.
 */
public class DBConnection {
    private String DBAddress = "";
    private int DBPort = 0;

    public DBConnection() {
    }

    public DBConnection(String dBAddress, int dBPort) {
        this.DBAddress = dBAddress;
        this.DBPort = dBPort;
    }
    public static DBConnection from(String config) {
        String[] array = config.split(":");
        if (array.length == 2) {
            return new DBConnection(array[0], Integer.parseInt(array[1]));
        } else {
            return new DBConnection(array[0], Integer.parseInt(array[1]));
        }
    }

    public String getDBAddress() {
        return this.DBAddress;
    }

    public void setDBAddress(String dBAddress) {
        this.DBAddress = dBAddress;
    }

    public int getDBPort() {
        return this.DBPort;
    }

    public void setDBPort(int dBPort) {
        this.DBPort = dBPort;
    }
}
