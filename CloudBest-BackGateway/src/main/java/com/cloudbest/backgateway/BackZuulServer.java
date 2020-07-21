package com.cloudbest.backgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
public class BackZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(BackZuulServer.class,args);
    }

    /**
     * Desc: swagger2 接口文档整合网关
     * @author : hdq
     * @date : 2020/7/21 11:37
     */
    @Component
    @Primary
    class DocumentationConfig implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> resources = new ArrayList<SwaggerResource>();

            resources.add(swaggerResource("用户服务","/user/v2/api-docs?group=admin","2.0"));
            resources.add(swaggerResource("商品服务","/items/v2/api-docs?group=admin","2.0"));
            resources.add(swaggerResource("订单服务","/order/v2/api-docs?group=admin","2.0"));
            resources.add(swaggerResource("交易服务","/payment/v2/api-docs?group=admin","2.0"));
            resources.add(swaggerResource("搜索服务","/search/v2/api-docs?group=admin","2.0"));
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
