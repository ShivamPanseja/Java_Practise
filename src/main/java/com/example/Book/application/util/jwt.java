package com.example.book.application.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class jwt {

    // Must be at least 32 characters for HS256
    private static final String SECRET = "mySuperSecretKeyForJwtToken123456";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 86400000))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}