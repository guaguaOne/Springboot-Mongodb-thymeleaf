package com.tumei.yxwd;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016/11/24 0024.
 *
 * 游戏的特有配置
 */
@Configuration
@ConfigurationProperties(prefix = "yxwd")
public class YxwdConfig {
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
