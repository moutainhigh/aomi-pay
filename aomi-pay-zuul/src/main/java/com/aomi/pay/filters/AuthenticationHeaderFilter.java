package com.aomi.pay.filters;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
public class AuthenticationHeaderFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AuthenticationHeaderFilter.class);
	
	private static final String secretKey = "KBS-20188888";

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
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String uri = request.getRequestURI().toString().toLowerCase();
		String method = request.getMethod();
		log.info(String.format("====AuthenticationHeaderFilter.run - %s request to %s", request.getMethod(), uri));

		//TODO 后续加api限制
		return null;
	}
	
	private void stopZuulRoutingWithError(RequestContext ctx, HttpStatus status, String responseText) {

		ctx.removeRouteHost();
		ctx.setResponseStatusCode(status.value());
		ctx.setResponseBody(responseText);
		ctx.setSendZuulResponse(false);
	}

}
