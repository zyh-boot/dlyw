package com.cx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetSocketAddress;

/**
 * @author Administrator·
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableTransactionManagement
@MapperScan({"com.cx.*.mapper", "com.cx.module.*.mapper"})
public class AppApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AppApplication.class)
                .run(args);

    }
}