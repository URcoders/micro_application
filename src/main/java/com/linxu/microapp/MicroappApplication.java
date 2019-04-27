package com.linxu.microapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.annotation.WebServlet;

/**
 * @author linxu
 * @date 2019/4/5
 * starter of application
 */
@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration()
@ServletComponentScan
@EnableTransactionManagement
public class MicroappApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroappApplication.class, args);
    }

}
