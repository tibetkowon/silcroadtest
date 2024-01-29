package com.example.demo.config.jwt;

import com.example.demo.controller.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long expiration;
    private final long refreshExpiration;


    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
    }
    public TokenDto makeToken(Long userId) {
        return TokenDto.builder()
                .accessToken(createAccessToken(userId))
                .refreshToken(createRefreshToken(userId))
                .build();
    }

    private String createAccessToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusDays(expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createRefreshToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", "refresh-" + userId);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusDays(refreshExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserId(String token) {
        return getClaims(token).getBody()
                .get("userId", Long.class);
    }

    public boolean isNotExpired(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            Date expiredDate = claims.getBody()
                    .getExpiration();
            Date now = new Date();
            return expiredDate.after(now);
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("JWT token has expired", e);
        } catch (SecurityException
                 | MalformedJwtException
                 | UnsupportedJwtException
                 | IllegalArgumentException e) {
            throw new BadCredentialsException("Error validating JWT token", e);
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}