package com.linxu.microapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author linxu
 * @date 2019/4/5
 * starter of application
 */
@SpringBootApplication
@EnableScheduling
public class MicroappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroappApplication.class, args);
    }

}
