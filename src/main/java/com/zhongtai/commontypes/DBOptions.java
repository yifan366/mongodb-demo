package com.zhongtai.commontypes;

/**
 * Created by rain on 2016/12/13.
 */
public class DBOptions {
    private boolean AutoConnectRetry = true;
    private int ConnectionsPerHost = 20;
    private int ConnectTimeout = 5000;
    private int MaxWaitTime = 12000;
    private boolean SocketKeepAlive = false;
    private int SocketTimeout = 100;
    private int ThreadsPerConnection = 50;

    public DBOptions() {
    }

    public boolean isAutoConnectRetry() {
        return this.AutoConnectRetry;
    }

    public void setAutoConnectRetry(boolean autoConnectRetry) {
        this.AutoConnectRetry = autoConnectRetry;
    }

    public int getConnectionsPerHost() {
        return this.ConnectionsPerHost;
    }

    public void setConnectionsPerHost(int connectionsPerHost) {
        this.ConnectionsPerHost = connectionsPerHost;
    }

    public int getConnectTimeout() {
        return this.ConnectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.ConnectTimeout = connectTimeout;
    }

    public int getMaxWaitTime() {
        return this.MaxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.MaxWaitTime = maxWaitTime;
    }

    public boolean isSocketKeepAlive() {
        return this.SocketKeepAlive;
    }

    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.SocketKeepAlive = socketKeepAlive;
    }

    public int getSocketTimeout() {
        return this.SocketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.SocketTimeout = socketTimeout;
    }

    public int getThreadsPerConnection() {
        return this.ThreadsPerConnection;
    }

    public void setThreadsPerConnection(int threadsPerConnection) {
        this.ThreadsPerConnection = threadsPerConnection;
    }
}
