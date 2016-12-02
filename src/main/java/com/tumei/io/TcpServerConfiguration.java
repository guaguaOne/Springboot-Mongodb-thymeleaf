package com.tumei.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
@Configuration
@EnableConfigurationProperties(TcpServerConfigurationProperty.class)
public class TcpServerConfiguration {
    @Autowired
    private TcpServerConfigurationProperty property;

    @Bean
    public TcpServer getTcpServer() {
        TcpServer server = new TcpServer();
        server.setHost(property.getHost());
        server.setPort(property.getPort());
        server.setBacklog(property.getBacklog());
        return server;
    }

}
