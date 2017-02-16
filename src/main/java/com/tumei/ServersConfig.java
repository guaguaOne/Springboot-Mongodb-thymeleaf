package com.tumei;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by niannian on 2017/2/16.
 */
@Component
@ConfigurationProperties(prefix = "servers")
public class ServersConfig {
    /**
     * 读取配置的服务器
     */
    private String[] ip;

    public String[] getIp(){
        return ip;
    }
    public void setIp(String[] ip){
        this.ip=ip;
    }
}
