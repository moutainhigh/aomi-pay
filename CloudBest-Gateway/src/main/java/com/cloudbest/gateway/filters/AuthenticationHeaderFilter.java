package com.cloudbest.gateway.filters;


import com.cloudbest.common.util.TokenUtil;
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

	private static Logger log = LoggerFactory.getLogger(com.cloudbest.gateway.filters.AuthenticationHeaderFilter.class);
	
	private static final String secretKey = "KBS-20188888";
	
	private static final String[][] preAuthenticationIgnoreUris = {
		{"/app/user/sendSms", "*"},
		{"app/items/catagory/query/queryCatagory", "*"},
		{"app/items/items/query/queryItems", "*"},
		{"app/items/items/query/queryFavoriteItems", "*"},
		{"app/items/items/query/getItemInfo", "*"},
		{"app/items/items/query/getItemInfoSku", "*"},
		{"app/items/items/query/totalItemsPrice", "*"},
	};
	
//	所有request都需要验证Token，除了上面ignore的
	private static final String[][] preAuthenticationMustUris = {
			{"/", "*"}
	};
	
	//If request method is POST
//	private static final String[][] preAuthenticationMustUris = {
//		{"/user", "POST"}
//	};

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

		log.info(String.format("====AuthenticationHeaderFilter.shouldFilter - http method: (%s)", method));
		log.info(String.format("====AuthenticationHeaderFilter.shouldFilter - url", uri));
		for (int i=0; i<preAuthenticationIgnoreUris.length; i++) {
			if (uri.startsWith(preAuthenticationIgnoreUris[i][0].toLowerCase()) && 
					(preAuthenticationIgnoreUris[i][1].equals("*") || method.equalsIgnoreCase(preAuthenticationIgnoreUris[i][1])) ) {
				log.info("this will be not use filter");
				return false;
			}
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

		if(!uri.contains("app")&&!uri.contains("brand")){
			this.stopZuulRoutingWithError(ctx, HttpStatus.UNAUTHORIZED, "Go out!Allow intranet access only!：(" + request.getRequestURI().toString() + ")");
			return null;
		}
//		boolean token=this.token(request);
//		if(token==false){
//			this.stopZuulRoutingWithError(ctx, HttpStatus.UNAUTHORIZED, "Invalid User Token for the API !：(" + request.getRequestURI().toString() + ")");
//			return null;
//		}
		boolean falg = this.getpassByUrl(request);
		if(falg == false) {
			this.stopZuulRoutingWithError(ctx, HttpStatus.UNAUTHORIZED, "Go out!Allow intranet access only!：(" + request.getRequestURI().toString() + ")");
			return null;
		}
		// 1. clear userInfo from HTTP header to avoid fraud attack
		ctx.addZuulRequestHeader("user-info", "");
		
		// 2. verify the passed user token
		String userToken = request.getHeader("UserToken");
		
//		log.info(String.format("====AuthenticationHeaderFilter.run - UserToken: %s",userToken));
		Claims claims = null;
		String userInfo = null;
//		log.info("Judge userToken");
		if (userToken != null && !userToken.trim().equals("null") && !userToken.trim().equals("")) {
			try {
				log.info("UserToken has value.....");
	            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(userToken).getBody();
	            log.info("claims is:"+claims);
	            
	            String userId="";
	            try{
	            	userId = (String)claims.get("userId");
	            	log.info("这里是String类型");
	            }catch(Exception ex){
	            	userId = ((Integer)claims.get("userId")).toString();
	            	
	            	log.info("这里是Integer类型");
	            }
	            
	            log.info("userId is:"+userId);
	            String userName = (String)claims.get("userName");
	            log.info("userId is:"+userId);
	            log.info("userName is:"+userName);
	            userInfo = "{\"id\":\"" + userId + "\", \"name\":\"" + userName + "\"}";
	            log.info("userInfo is:"+userInfo);
	        } catch (SignatureException e) {
	        	this.stopZuulRoutingWithError(ctx, HttpStatus.UNAUTHORIZED, "Invalid User Token for the API (" + request.getRequestURI().toString() + ")");
				return null;
	        } catch (ExpiredJwtException e) {
	        	this.stopZuulRoutingWithError(ctx, HttpStatus.UNAUTHORIZED, "Expired User Token for the API (" + request.getRequestURI().toString() + ")");
				return null;
	        }
		}
		
//		log.info(String.format("=====AuthenticationHeaderFilter.run - userInfo: %s", userInfo));
		// 3. set userInfo to HTTP header
		if (userInfo != null) {
			
			String encodeUserInfo=userInfo;
			
			try {
				encodeUserInfo=URLEncoder.encode(userInfo,"UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ctx.addZuulRequestHeader("user-info", encodeUserInfo);
		}
		
//		log.info(String.format("====AuthenticationHeaderFilter.run - userInfo: %s", request.getHeader("user-info")));
//		log.info(String.format("====AuthenticationHeaderFilter.run - userInfo: %s", ctx.getZuulRequestHeaders().get("user-info")));
		
		// 4. stop the filter chain if userInfo is must
		/*if (userInfo == null) {
			for (int i=0; i<preAuthenticationMustUris.length; i++) {
				if (uri.startsWith(preAuthenticationMustUris[i][0].toLowerCase()) && 
						(preAuthenticationMustUris[i][1].equals("*") || method.equalsIgnoreCase(preAuthenticationMustUris[i][1])) ) {
					log.info(String.format("userInfo is missed for %s", uri));
					
					this.stopZuulRoutingWithError(ctx, HttpStatus.UNAUTHORIZED, "User Login is needed for the API (" + request.getRequestURI().toString() + ")");
					
					return null;
				}
			}
		}*/
		
		return null;
	}
	
	private void stopZuulRoutingWithError(RequestContext ctx, HttpStatus status, String responseText) {

		ctx.removeRouteHost();
		ctx.setResponseStatusCode(status.value());
		ctx.setResponseBody(responseText);
		ctx.setSendZuulResponse(false);
	}
	
	
	//访问黑名单地址
	private static final String[] notPassurl = new String[] {
//			"/app/user/queryAddr", //查询用户地址
//			"/app/user/select",  //根据用户id查询用户信息
//			"/app/item/query/skuid/stock", //查询库存信息和商品信息
//			"/app/items/query/skuid/img",//查询图片
//			"/app/items/check/lock", //查库存和锁库存
//			"/app/items/query/skuid/img",
//			"/app/items/items/query/getItemInfoById",
//			"/app/items/user/query/getItemInfoById",
//			"/app/items/check/lock",
//			"/app/item/query/skuid/stock",
//			"/app/item/query/skuid/updateSaleVolume",
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

	//token 认证
	public boolean token(HttpServletRequest request){
		String uri = request.getRequestURI();
		String token = uri.substring(uri.lastIndexOf("/")).replaceAll("/", "");
		System.out.println(token);
		try {
		TokenUtil.getUserId(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
