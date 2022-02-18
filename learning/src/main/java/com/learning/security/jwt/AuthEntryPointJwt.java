package com.learning.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	  @Override
	  //This method is gonna take care of all the exceptions regarding the unauthorized access
	  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
	      throws IOException, ServletException {
	    logger.error("Unauthorized error: {}", authException.getMessage());

	    //Through this we are gonna set the info back 
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    // this response it is of json type.
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    // status code as unauthorized

	    //This entire info will be put into the map
	    //and set is key value pair in terms of JSON
	    final Map<String, Object> body = new HashMap<>();
	    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
	    body.put("error", "Unauthorized");
	    body.put("message", authException.getMessage());
	    body.put("path", request.getServletPath());

	    
	    //used to render the content interms of JSON 
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getOutputStream(), body);
	  }

}
