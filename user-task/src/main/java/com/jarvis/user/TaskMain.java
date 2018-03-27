package com.jarvis.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by jian on 18-3-9
 */
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(TaskProperties.class)
public class TaskMain {

    public static void main(String[] args) {
        SpringApplication.run(TaskMain.class, args);
    }

    @Bean
    public TaskExecutor workerTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(100);

        return threadPoolTaskExecutor;
    }

    @Bean
    public TaskScheduler workerTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);

        return threadPoolTaskScheduler;
    }

}
