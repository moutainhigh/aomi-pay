package com.aomi.pay;

import com.aomi.pay.filters.AuthenticationHeaderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdq
 */
@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
@ComponentScan("com.aomi.pay.config")
public class ZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServer.class,args);
    }
    @Bean
    public AuthenticationHeaderFilter authenticationHeadFilter() {
        return new AuthenticationHeaderFilter();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("12800000KB");
        factory.setMaxRequestSize("12800000KB");
        return factory.createMultipartConfig();
    }

    //TODO  token认证过滤器

    @Bean
    public FilterRegistrationBean characterEncodingFilter() {
        FilterRegistrationBean filter=new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        filter.setFilter(characterEncodingFilter);
        return filter;
    }

    /**
     * Desc: swagger2 接口文档整合网关
     * @author : hdq
     * @date : 2020/7/14 15:37
     */
    @Component
    @Primary
    class DocumentationConfig implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> resources = new ArrayList<SwaggerResource>();

            resources.add(swaggerResource("环迅api调用服务","/api/v2/api-docs","2.0"));
            resources.add(swaggerResource("user服务","/user/v2/api-docs","2.0"));
            resources.add(swaggerResource("order服务","/order/v2/api-docs","2.0"));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }
    }

}
