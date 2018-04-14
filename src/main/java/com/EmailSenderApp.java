package com;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by Nir.
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnablePrometheusEndpoint
public class EmailSenderApp {

    public static void main(String[] args){
        SpringApplication.run(EmailSenderApp.class, args);
    }
}
