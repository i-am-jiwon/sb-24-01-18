package com.ll.sb240118.global.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static String encode(Map<String, String> data) {
        Claims claims = Jwts
                .claims()
                .setSubject("sb-24-01-18 jwt")
                .add("type", "access_token")
                .add("data", data)
                .build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000 * 60 * 5);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890")
                .compact();
    }
}