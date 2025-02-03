package com.atomika.gitByCity.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${app.corePoolSize}")
    private int corePoolSize;

    @Value("${app.maxPoolSize}")
    private int maxPoolSize;

    @Value("${app.queueCapacity}")
    private int queueCapacity;

    @Value("${app.threadNamePrefix}")
    private String threadNamePrefix;

    @Bean(name = "asyncTaskExecutor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        taskExecutor.initialize();

        return taskExecutor;
    }
}
