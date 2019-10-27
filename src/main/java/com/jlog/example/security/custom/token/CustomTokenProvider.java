package com.jlog.example.security.custom.token;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Duration;
import java.util.Date;

@Slf4j
public class CustomTokenProvider {

    private Duration tokenExpiration;
    private String tokenSecret;

    public CustomTokenProvider(Duration tokenExpiration, String tokenSecret) {
        this.tokenExpiration = tokenExpiration;
        this.tokenSecret = tokenSecret;
    }

    public String createToken(Authentication authentication) {

        String userId = null;

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            userId = oAuth2User.getAttribute("email");
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpiration.toMillis());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
