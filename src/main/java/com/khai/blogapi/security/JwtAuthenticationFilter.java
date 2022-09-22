package com.khai.blogapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.khai.blogapi.service.CustomUserService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	CustomUserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = tokenProvider.resolveToken(request);
			if(tokenProvider.validateToken(token) && StringUtils.hasText(token)) {
				
				Long userId = tokenProvider.getUserIdFromJwt(token);
				UserPrincipal userPrincipal = userService.loadUserByUserId(userId);
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(
								userPrincipal, 
								null,
								userPrincipal.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}catch(Exception e) {
			log.error("Could not validate this jwt");
		}
		
		filterChain.doFilter(request, response);
	}

}
