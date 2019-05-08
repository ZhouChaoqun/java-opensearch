package com.quancheng.application;

import com.quancheng.starter.log.QcLoggable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableEurekaClient
@ComponentScan({ "com.saluki.client", "com.quancheng"})
@ImportResource({ "classpath*:saluki.xml", "classpath*:quancheng-*.xml","classpath*:datasource.xml", })
@SpringBootApplication
@EnableAsync
public class Application {

    @QcLoggable(QcLoggable.Type.NONE)
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setRegisterShutdownHook(false);
        app.run(args);
    }
}
