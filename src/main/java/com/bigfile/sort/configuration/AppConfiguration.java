package com.bigfile.sort.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class AppConfiguration {

    @Bean
    public Queue<Integer> queue() {
        return new ConcurrentLinkedQueue<>();
    }
}
