package com.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 启动类
 */
@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MultiThreadDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiThreadDemoApplication.class,args);
    }
}