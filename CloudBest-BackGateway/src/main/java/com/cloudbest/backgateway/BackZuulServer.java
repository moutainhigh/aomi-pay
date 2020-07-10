package com.cloudbest.backgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class BackZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(BackZuulServer.class,args);
    }
}
