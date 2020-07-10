package com.cloudbest.backgateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BackZuulFiler extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI().toString().toLowerCase();
        String method = request.getMethod();
//        if(uri.contains("app")){
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//        }
        boolean passurl=this.getpassByUrl(request);
        if(passurl==false){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
    //后端工程于工程之间调用接口内部访问
    private static final String[] notPassurl = new String[] {

    };

    //请求验证
    public boolean getpassByUrl(HttpServletRequest request) {
        String uri = request.getRequestURI().toString().toLowerCase();
        for (int i = 0; i < notPassurl.length; i++) {
            if (uri.contains(notPassurl[i].toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
