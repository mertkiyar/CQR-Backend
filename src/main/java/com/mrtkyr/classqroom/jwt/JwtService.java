package com.mrtkyr.classqroom.jwt;

import com.mrtkyr.classqroom.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        User user = (User) userDetails;
        Map<String, String> claimsMap = new HashMap<>();
        claimsMap.put("role", user.getUserType().name());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claimsMap)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*72)) // the token will be expired in 72 hours
                .signWith(key)
                .compact();
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsTFunction.apply(claims);
    }

    public String getUsernameByToken(String token){
        return exportToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = exportToken(token, Claims::getExpiration);
        return new Date().after(expiredDate);
    }
}
