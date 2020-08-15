package com.aomi.pay.util;

import com.aomi.pay.constants.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

	private static final Logger LOG = LoggerFactory.getLogger(TokenUtil.class);

	public static String  createToken(long customerId, String phone){
		String userToken = Jwts.builder()
				.setSubject("JuHei")
				.setExpiration(new Date(new Date().getTime() + 1000*60*60*24*180))
				.claim("customerId", customerId)
				.claim("phone",phone)
		.signWith(SignatureAlgorithm.HS512, CommonConstants.SECRETKEY).compact();
		
		return userToken;
	}
	public static String  createToken(Long userId){
		String userToken = Jwts.builder()
				.setSubject("JuHei")
				.setExpiration(new Date(new Date().getTime() + 1000*60*60*24*180))
				.claim("userId",userId)
				.signWith(SignatureAlgorithm.HS512, CommonConstants.SECRETKEY).compact();

		return userToken;
	}
	public  static long getUserId(String token) throws Exception{
		
		Claims claims = Jwts.parser().setSigningKey(CommonConstants.SECRETKEY).parseClaimsJws(token).getBody();
		Map map  = new HashMap();
		long customerId = Long.valueOf(String.valueOf(claims.get("customerId")));
	    return customerId;
	}
	
	
	public  static String getUserPhone(String token) throws Exception{

		Claims claims = Jwts.parser().setSigningKey(CommonConstants.SECRETKEY).parseClaimsJws(token).getBody();
		Map map  = new HashMap();
		String phone = (String)claims.get("phone");
	    return phone;
	}

	
	
	
	/**验证token*/
	public static Object  validToken(String token) throws Exception{
		
		Map map = new HashMap();
		long userId;
		try {
			userId = TokenUtil.getUserId(token);
		} catch (Exception e) {
			map.put(CommonConstants.RESP_CODE,CommonConstants.ERROR_TOKEN);
			map.put(CommonConstants.RESP_MESSAGE, "token无效");
			return map;		
		}	
		
		return userId;
	
	}
	
	
	
}
