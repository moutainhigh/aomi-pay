package com.aomi.pay.filters;


import com.aomi.pay.util.CommonExceptionUtils;
import com.aomi.pay.util.RegexUtils;
import com.aomi.pay.util.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hdq
 */
@Slf4j
public class AuthenticationHeaderFilter extends ZuulFilter {

    private static final String UTF_8 = "UTF-8";

    private static final int MERCHANT_LENGTH = 20;


    /*private static final String[] ignoreUris = {
            "api", "user", "order", "dictionary", "h5","doc.html","api-docs"
    };*/

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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI().toString().toLowerCase();
        String method = request.getMethod();
        try {
            request.setCharacterEncoding(UTF_8);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info(String.format("====AuthenticationHeaderFilter.shouldFilter - http method: (%s)", method));
        log.info(String.format("====AuthenticationHeaderFilter.shouldFilter - url", uri));
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI().toString().toLowerCase();
        String method = request.getMethod();
        //不符合路由规则踢出
        /*if (!isPrefixRoute(uri) && !isMerchantRoute(uri)) {
            this.stopZuulRoutingWithError(ctx, HttpStatus.UNAUTHORIZED, "Does not comply with routing rules!：(" + request.getRequestURI().toString() + ")");
            return null;
        } else if (!isPrefixRoute(uri) && isMerchantRoute(uri)) {
            updateUrl(ctx);
        }*/
        log.info(String.format("====AuthenticationHeaderFilter.run - %s request to %s", request.getMethod(), uri));
        return null;
    }

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 如果入参为纯数字且为20位就去请求order服务getUserId接口
     **/
    /*private String updateUrl(RequestContext ctx) {
        HttpServletRequest request = ctx.getRequest();

        String uri = request.getRequestURI().toString().toLowerCase();

        try {
            InputStream in = request.getInputStream();

            // 请求方法
            String method = request.getMethod();

            // 校验成功
            String body = StreamUtils.copyToString(in, Charset.forName(UTF_8));
            log.info("body:{}", body);
            // 如果body为空初始化为空json
            if (StringUtil.isBlank(body)) {
                body = "{}";
            }
            //body = "/order/aaaaa";
            log.info("转换后的body:{}", body);

            // 转化成json
            JSONObject json = JSONObject.fromObject(body);

            String merchantId = uri.replaceAll("/", "");

            // get方法和post、put方法处理方式不同
            if ("GET".equals(method)) {
                // post和put需重写HttpServletRequestWrapper
                json.put("merchantId", merchantId);
                String newBody = json.toString();
                log.info("newBody" + newBody);
                final byte[] reqBodyBytes = newBody.getBytes();
                // 重写上下文的HttpServletRequestWrapper
                ctx.setRequest(new HttpServletRequestWrapper(request) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }

                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            }
            ctx.put(FilterConstants.REQUEST_URI_KEY, "/order/alipay/getUserId");
            return null;
        } catch (IOException e) {
            log.error("重构请求失败异常:{}", e);
            CommonExceptionUtils.throwSystemException("重构请求失败");
        }
        return null;

    }*/

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 是否是加前缀指定服务的请求
     **/
    /*private boolean isPrefixRoute(String uri) {
        boolean flag = false;
        if (StringUtil.isNotBlank(uri)) {
            for (String ignoreUri : ignoreUris) {
                if (uri.contains(ignoreUri)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }*/

    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 是否拼接商户号的请求
     **/
    /*private boolean isMerchantRoute(String uri) {
        boolean flag = false;
        String uriString = uri.replaceAll("/", "");
        if (StringUtil.isNotBlank(uri) && RegexUtils.isNumber(uriString) && uriString.length() == MERCHANT_LENGTH) {
            flag = true;
        }
        return flag;
    }*/


    private void stopZuulRoutingWithError(RequestContext ctx, HttpStatus status, String responseText) {
        ctx.removeRouteHost();
        ctx.setResponseStatusCode(status.value());
        ctx.setResponseBody(responseText);
        ctx.setSendZuulResponse(false);
    }
}
