package com.example.demomv.security.jwt;

import com.example.demomv.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret.access}")
    private String jwtAccessSecret;
    @Value("${jwt.secret.refresh}")
    private String jwtRefreshSecret;
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateAccessJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return generateAccsesToken(userPrincipal);
    }

    private String generateAccsesToken(UserDetailsImpl userPrincipal) {
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(jwtAccessSecret), SignatureAlgorithm.HS256)
                .claim("roles", userPrincipal.getAuthorities())
                .claim("email", userPrincipal.getEmail())
                .compact();
    }

    public String generateAccessJwtToken(UserDetailsImpl userPrincipal) {

        return generateAccsesToken(userPrincipal);
    }


    public String generateRefreshJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return generateRefreshToken(userPrincipal);
    }

    private String generateRefreshToken(UserDetailsImpl userPrincipal) {
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs * 30L))
                .signWith(key(jwtRefreshSecret), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshJwtToken(UserDetailsImpl userPrincipal) {


        return generateRefreshToken(userPrincipal);
    }

    private Key key(String secret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key(jwtAccessSecret)).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateAccessToken(String token) {
        return validateJwtToken(token, jwtAccessSecret);
    }

    public boolean validateRefreshToken(String token) {
        return validateJwtToken(token, jwtRefreshSecret);
    }

    private boolean validateJwtToken(String authToken, String secret) {
        try {
            Jwts.parserBuilder().setSigningKey(key(secret)).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(String token, String secret) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }
}

