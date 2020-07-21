package com.cloudbest.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cloudbest
 * @Package: com.cloudbest.gateway.filters
 * @ClassName: ForwardSwaggerFilter
 * @Author: hdq
 * @Description: 不更改zuul规则的情况下，重定向swagger映射路径
 * @Date: 2020/7/21 15:23
 * @Version: 1.0
 */
@Component
public class ForwardSwaggerFilter extends ZuulFilter {


    private static Logger log = LoggerFactory.getLogger(ForwardSwaggerFilter.class);

    @Override
    public String filterType() {
        //四种类型：pre,routing,error,post
        //pre：主要用在路由映射的阶段是寻找路由映射表的
        //routing:具体的路由转发过滤器是在routing路由器，具体的请求转发的时候会调用
        //error:一旦前面的过滤器出错了，会调用error过滤器。
        //post:当routing，error运行完后才会调用该过滤器，是在最后阶段的
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
     */
    @Override
    public int filterOrder() {
        //执行顺序晚于自带filter
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    /**
     * 控制过滤器生效不生效
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 执行过滤逻辑（动态路由拼接子系统前缀context-path）
     *
     * @return
     */
    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();
        String originalRequestPath = context.get(FilterConstants.REQUEST_URI_KEY).toString();

        String modifiedRequestPath = originalRequestPath;
        //TODO 现阶段更改请求swagger的路径，投射到后台网关上的swagger集成上
        if (originalRequestPath.contains("/v2/api-docs")&&originalRequestPath.contains("/app/")) {
            modifiedRequestPath = modifiedRequestPath.replace("/app/", "/");
            log.info("modifiedRequestPath:{}",modifiedRequestPath);
        }

        context.put(FilterConstants.REQUEST_URI_KEY, modifiedRequestPath);
        return null;
    }
}
