package org.ifmo.laba4.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "mySecretKey123456mySecretKey123456";
    String base64SecretKey = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;//сутки


    public String generateToken(String username) {

        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(base64SecretKey), SignatureAlgorithm.HS256.getJcaName());


        return Jwts.builder()
                .setSubject(username)
                .claim("roles", List.of("USER"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), "HmacSHA256");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    } //non-use
}
