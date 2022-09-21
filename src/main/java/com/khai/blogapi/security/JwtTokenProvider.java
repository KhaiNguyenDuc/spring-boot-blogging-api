package com.khai.blogapi.security;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwtSecret}")
	private String secret;
	
	@Value("${app.jwtExpiration}")
	private Long expiration;
	
	
	public String generateToken(Authentication auth) {
		
		UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
		
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		
		Date now = new Date();
		
		String jws = Jwts.builder()
				.setSubject(userPrincipal.getId().toString())
				.signWith(key)
				.setExpiration(new Date(now.getTime() + expiration))
				.compact();

		return jws;
	}
	
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	public Long getUserIdFromJwt(String jwt) {
		
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jwt)
			.getBody();
		
		return Long.valueOf(claims.getSubject());
	}
	

	public boolean validateToken(String authToken) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(authToken);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
}
