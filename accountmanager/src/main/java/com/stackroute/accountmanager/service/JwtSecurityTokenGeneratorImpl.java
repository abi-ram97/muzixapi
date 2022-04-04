package com.stackroute.accountmanager.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.stackroute.accountmanager.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator {

	@Override
	public Map<String, String> generateToken(User user) {
		String jwtToken = "";
		jwtToken = Jwts.builder().setSubject(user.getUserId())
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretKey")
				.compact();
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", jwtToken);
		map.put("message", "User successfully logged in");
		return map;
	}

}
