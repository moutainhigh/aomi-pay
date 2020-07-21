package com.cloudbest.gateway;

import com.cloudbest.gateway.filters.AuthenticationHeaderFilter;
import com.cloudbest.gateway.filters.ForwardSwaggerFilter;
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

//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
@ComponentScan("com.cloudbest.gateway.config")
public class ZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServer.class,args);
    }
    @Bean
    public AuthenticationHeaderFilter authenticationHeadFilter() {
        return new AuthenticationHeaderFilter();
    }

    @Bean
    public ForwardSwaggerFilter forwardSwaggerFilter(){
        return new ForwardSwaggerFilter();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("12800000KB");
        factory.setMaxRequestSize("12800000KB");
        return factory.createMultipartConfig();
    }

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

            resources.add(swaggerResource("用户服务","/app/user/v2/api-docs?group=app","2.0"));
            resources.add(swaggerResource("商品服务","/app/items/v2/api-docs?group=app","2.0"));
            resources.add(swaggerResource("订单服务","/app/order/v2/api-docs?group=app","2.0"));
            resources.add(swaggerResource("交易服务","/app/payment/v2/api-docs?group=app","2.0"));
            resources.add(swaggerResource("搜索服务","/app/search/v2/api-docs?group=app","2.0"));
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

//    @Bean
//    public CorsFilter corsFilter(){
//
//        final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config=new CorsConfiguration();
//
//        config.setAllowCredentials(true);
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        config.addAllowedOrigin("*");
//		/*config.addAllowedOrigin("http://127.0.0.1");
//		config.addAllowedOrigin("http://192.168.31.84");
//		config.addAllowedOrigin("http://192.168.31.1");*/
//        //config.addAllowedOrigin("http://api.yqilai.cn");
//        //config.addAllowedOrigin("http://wechat.yqilai.cn");
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//
//    }


//    @Bean
//    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
//                tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//
//                    @Override
//                    public void customize(Connector connector) {
//                        connector.setMaxPostSize(100000000);
//                    }
//
//                });
//
//            }
//        };
//    }
}
