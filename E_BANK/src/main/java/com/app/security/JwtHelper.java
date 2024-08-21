package com.app.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;
    
    private String secret= "AnkurChavanBeedAyushiSahuBhillaiVaradKulkarniBeedVighneshMasurkarAmboliChetanNarvekarAmboli";
    
    public String getUsernameFromToken(String token) {
    	return getClaimsFromToken(token, Claims::getSubject);
    }
    
    public <T> T getClaimsFromToken(String token, Function<Claims , T>claimsResolver) {
    	final Claims claims = getAllClaimsFromToken(token);
    	return claimsResolver.apply(claims);
    }
    
    private Claims getAllClaimsFromToken(String token) {
    	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    private Date getExpirationDateFromToken(String token) {
    	return getClaimsFromToken(token, Claims::getExpiration);
    }
    
    private Boolean isTokenExpired(String token) {
    	final Date expiration = getExpirationDateFromToken(token);
    	return expiration.before(new Date());
    }
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }
    
    public String doGenerateToken(Map<String, Object> claims, String subject) {
    	return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY*1000))
    			.signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    
    public Boolean validateToken(String token, UserDetails userDeatils) {
    	final String username = getUsernameFromToken(token);
    	return (username.equals(userDeatils.getUsername()) && !isTokenExpired(token));
    }

}