package com.bigfile.sort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BigfilesorterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigfilesorterApplication.class, args);
    }

}
