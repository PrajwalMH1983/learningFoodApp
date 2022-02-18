package com.learning.security.jwt;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learning.security.services.UserDetailsServiceImpl;

//This should be responsible for managing the token
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	
	//this method is actually responsible for validating your token
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//we need to parse the token
		//So we need to retrieve the token from the request
		String jwt = parseJwt(request);
		
		try {
			if(jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String userName = jwtUtils.getUserNameFromJwtToken(jwt);
				//We can retrieve the user details using userName
				//And based on this we can create an instance of UserNamePasswordAuthenticationToken
				//And this one we can set it into Authentication 
				//And that authentication object we can get via AuthenticationManager
				//Refer Security Diagram
				
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
				//We have loaded the username
				
				//Principal Object --> userDetails
				//credentials we have provided null cuz authentication is already done
				//getAuthorities() --> will retrieve the roles
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				//From this request we are going to propogate the token and other details 
				//So that later controller needs to retrieve some data from the request
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
				
				
				//purpose of security context is to store the details of the currently authenticated user 
				//when u are setting into the security context everytime it is going to use usernamePasswordAuthenticationToken
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Cannot set user authentication {}" , e);
		}
		
		filterChain.doFilter(request, response); 	//Internally it will call next filter / servlet(DS) Dispatcher Servlet
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		//jwt token will be placed inside the header
		//and it will be part of authorization header
		
		//StringUtils class has n number of methods 
		//StringUtils from the spring framework 
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());	//7th place onwards we will get our actual token
		}
		return null;
	}
}
