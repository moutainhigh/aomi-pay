package com.aomi.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : hdq
 */
@SpringBootApplication
@MapperScan("com.aomi.**.mapper.**")
@EnableScheduling
@EnableSwagger2
@EnableCaching
public class NotifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotifyApplication.class,args);
    }
}
