package com.daleyzou.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LearnDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnDemoApplication.class, args);
    }

}
