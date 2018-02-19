package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by Nir.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class EmailSenderApp {

    public static void main(String[] args){
        SpringApplication.run(EmailSenderApp.class, args);
    }
}
