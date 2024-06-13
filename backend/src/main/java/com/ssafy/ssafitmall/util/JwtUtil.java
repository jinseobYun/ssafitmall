package com.ssafy.ssafitmall.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import com.ssafy.ssafitmall.model.dto.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String key = "SSAFY_NonMajor_JavaTrack_SecretKey";
	private SecretKey secretkey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

	// id 받아서 토큰 만들기
	public String createToken(User user) {
		//만료시간
		Date exp = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
		return Jwts.builder().header().add("typ", "JWT").and().claim("userId", user.getUserId()).claim("userRole",user.getUserRole())
				.expiration(exp).signWith(secretkey)
				.compact();
	}
	// 실제로 확인하려는 용도가 아니고 유효기간이 지났다면 알아서 에러를 발생시키려고 함
	public Jws<Claims> validate(String token){
		return Jwts.parser().verifyWith(secretkey).build().parseSignedClaims(token);
	}
	
	
}
