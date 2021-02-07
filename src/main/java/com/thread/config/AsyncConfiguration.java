package com.thread.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 线程池配置
 */
@EnableAsync
@Configuration
public class AsyncConfiguration {

    /**
     * 发送短信的线程池配置
     */
    @Bean("msgExecutor")
    public TaskExecutor msgExecutor(){
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8); //核心线程数量
        executor.setMaxPoolSize(8); //最大线程数量
        executor.setQueueCapacity(10); //队列大小
        executor.setKeepAliveSeconds(60); //空闲回收时间
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy()); //拒绝策略
        executor.setThreadNamePrefix("msgExecutor-"); //线程名前缀
        return executor;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
