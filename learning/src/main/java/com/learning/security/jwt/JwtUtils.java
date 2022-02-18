package com.learning.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.learning.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//To provide the tokens
@Component  //--> means it will create 1 object
//Should be responsible for generating the tokens and validating the tokens
public class JwtUtils {
	
	//will generate the logs automatically
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	//read JwtSecret
	//To read the yml specificatons
	@Value("${learning.app.jwtSecret}")
	private String jwtSecret;
	
	//read expiry value
	@Value("${learning.app.jwtExpirationMs}")
	private int jwtExpiryValue;
	
	
	public String generateToken(Authentication authentication) {
		//retrieve userName 
		//issued @ when token is generated
		//expiry
		//encrypted strategy -- ex SHA512
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();	//getPrincipal will hold ur user details
		//userName
		//builder() -> build jwt object 
		return Jwts.builder()
					.setSubject(userPrincipal.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime() + jwtExpiryValue))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();	//This entire info it will encrypt into a single strip
	}
	
	//Jws Object --> is a transformed version of jwt in terms of java
	public String getUserNameFromJwtToken(String authToken) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(authToken)
				.getBody().getSubject();
	}

	//Validation of token 
	public boolean validateJwtToken(String authToken) {
		 try {
		        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
		        return true;
		      } catch (SignatureException e) {
		        logger.error("Invalid JWT signature: {}", e.getMessage());
		      } catch (MalformedJwtException e) {
		        logger.error("Invalid JWT token: {}", e.getMessage());
		      } catch (ExpiredJwtException e) {
		        logger.error("JWT token is expired: {}", e.getMessage());
		      } catch (UnsupportedJwtException e) {
		        logger.error("JWT token is unsupported: {}", e.getMessage());
		      } catch (IllegalArgumentException e) {
		        logger.error("JWT claims string is empty: {}", e.getMessage());
		      }

		      return false;
	}
}
