package com.example.demo.security;


import com.example.demo.user.dto.ExceptionMessage;
import com.example.demo.user.dto.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class JwtToken {


	public String generateToken(UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException {
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("userLoginData",userLoginDTO);
		return Jwts.builder()
				.claims(claims)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000000000))
				.signWith(RS256KeyPairSingleton.getInstance().getKeyPair().getPrivate())
				.compact();
	}

	public UserLoginDTO getUserFromToken(String token) throws ExceptionMessage {
		Claims claims = getClaims(token);
		if(claims.containsKey("userLoginData")){
			LinkedHashMap<String, Object> userLoginDataMap = (LinkedHashMap<String, Object>) claims.get("userLoginData");
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(userLoginDataMap, UserLoginDTO.class);
		} else {
			throw new ExceptionMessage("UserLoginData not found in token");
		}
	}


	private static Claims getClaims(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey( RS256KeyPairSingleton.getInstance().getKeyPair().getPublic())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims;
	}

}
