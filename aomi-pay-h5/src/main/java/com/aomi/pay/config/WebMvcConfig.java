package com.aomi.pay.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hdq
 * @Date 2020/8/13
 * @Version 1.0
 * @Desc web配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"
    };

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc addInterceptor：需要一个实现HandlerInterceptor接口的拦截器实例
     * addPathPatterns：用于设置拦截器的过滤路径规则；addPathPatterns("/**")对所有请求都拦截
     * excludePathPatterns：用于设置不需要拦截的过滤规则
     * 拦截器主要用途：进行用户登录状态的拦截，日志的拦截等。
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        List<String> excludeList = new ArrayList<String>();
        /*excludeList.add("/appLogin/login");
        excludeList.add("/error");*/
        registry.addInterceptor(new AuthorizedInterceptor()).addPathPatterns("/**").excludePathPatterns(excludeList);
    }

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 页面跳转
     * 在这里重写addViewControllers方法，并不会覆盖WebMvcAutoConfiguration（Springboot自动配置）中的addViewControllers
     * （在此方法中，Spring Boot将“/”映射至index.html），这也就意味着自己的配置和Spring Boot的自动配置同时有效
     **/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/hello3").setViewName("index");
    }

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 静态资源
     * 比如，我们想自定义静态资源映射目录的话，只需重写addResourceHandlers方法即可。
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry.addResourceHandler("/static/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);*/
    }

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 默认静态资源处理器
     **/
    /*@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
        configurer.enable("defaultServletName");
    }*/

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 配置请求视图映射
     **/
    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    /**
     * 视图配置
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        WebMvcConfigurer.super.configureViewResolvers(registry);
        registry.viewResolver(resourceViewResolver());
    }

    /**
     * @author  hdq
     * @date  2020/8/13
     * @desc 跨域
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/cors/**")
                .allowedHeaders("*")
                .allowedMethods("POST","GET")
                .allowedOrigins("*");
    }


    /**
     * 消息内容转换配置
     * 配置fastJson返回json转换
     * @param converters
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //调用父类的配置
        WebMvcConfigurer.super.configureMessageConverters(converters);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);
    }*/
}
