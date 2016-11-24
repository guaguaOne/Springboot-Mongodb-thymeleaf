package com.tumei;

import com.tumei.io.TcpServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
//@ConfigurationProperties(prefix = "runner")
@Component
public class RunnerBean implements CommandLineRunner, ApplicationContextAware {

    private ConfigurableApplicationContext ctx;
    private Log log = LogFactory.getLog(RunnerBean.class);

    // java -jar demo.jar --version="1.2.1" 通过命令行的命名参数传递到字段上
    @Value("${runner.version}")
    private String version;

    @Override
    public void run(String... strings) throws Exception {
//        log.info("Runner Bean run...");
//        String[] names = ctx.getBeanDefinitionNames();
//        Arrays.sort(names);
//        for (String bean : names)
//        {
//            log.info("注册: " + bean);
//        }
        log.info("ooo version:" + version);

        TcpServer ts = ctx.getBean(TcpServer.class);
        if (!ts.startServer()) {
            log.error("无法启动Tcp连接......");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = (ConfigurableApplicationContext)applicationContext;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
