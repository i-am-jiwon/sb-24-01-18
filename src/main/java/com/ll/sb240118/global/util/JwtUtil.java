package com.ll.sb240118.global.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";

    public static String encode(long expirationsSeconds, Map<String, Object> data) {
        Claims claims = Jwts
                .claims()
                .setSubject("sb-24-01-18 jwt")
                .add("data", data)
                .build();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expirationsSeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims decode(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

}