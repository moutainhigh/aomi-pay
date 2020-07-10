package com.cloudbest.items;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.cloudbest.items.mapper")
@EnableFeignClients
public class ItemsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemsApplication.class,args);
    }
}
