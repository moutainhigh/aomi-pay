package com.aomi.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : hdq
 * @date 2020/7/14 14:52
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String SWAGGER_SCAN_SEARCH_PACKAGE = "com.aomi.pay.controller";

    /**
     * @author  hdq
     * @desc swagger是否启用开关
     **/
    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createAppRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiAppInfo())
                .enable(enableSwagger)
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_SEARCH_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiAppInfo() {
        return new ApiInfoBuilder()
                .title("支付系统-user接口管理")
                .description("支付系统-用户接口文档")
                .contact("hdq")
                .version("1.0")
                .build();
    }

}
