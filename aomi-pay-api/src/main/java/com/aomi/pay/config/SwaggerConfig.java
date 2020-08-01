package com.aomi.pay.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    public static final String SWAGGER_SCAN_SEARCH_APP_PACKAGE = "com.aomi.pay.controller.app";

    public static final String SWAGGER_SCAN_SEARCH_ADMIN_PACKAGE = "com.aomi.pay.controller.admin";

    @Bean
    public Docket createAppRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("app")
                .apiInfo(apiAppInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_SEARCH_APP_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiAppInfo() {
        return new ApiInfoBuilder()
                .title("云上优选-搜索服务App管理接口")
                .description("云上优选-搜索服务App接口文档")
                .contact("hdq")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createAdminRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(apiAdminInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_SEARCH_ADMIN_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiAdminInfo() {
        return new ApiInfoBuilder()
                .title("云上优选-搜索服务后台管理接口")
                .description("云上优选-搜索服务后台接口文档")
                .contact("hdq")
                .version("1.0")
                .build();
    }
}
