package com.tumei.io;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
@ConfigurationProperties(prefix = "io.tcp")
public class TcpServerConfigurationProperty {
    /**
     * 服务器地址
     */
    private String host;

    /**
     * 服务器端口号
     */
    private int port;

    /**
     * 服务器监听队列长度
     */
    private int backlog;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public TcpServerConfigurationProperty() {}
}
