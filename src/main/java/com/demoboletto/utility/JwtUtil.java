package com.demoboletto.utility;

import com.demoboletto.constants.Constants;
import com.demoboletto.dto.response.JwtTokenDto;
import com.demoboletto.type.ERole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token.expire-period}")
    @Getter
    private Integer accessExpiration;

    @Value("${jwt.refresh-token.expire-period}")
    @Getter
    private Integer refreshExpiration;

    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public Claims validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Long id, ERole role, Integer expiration){
        Claims claims = Jwts.claims();
        claims.put(Constants.CLAIM_USER_ID, id);
        if (role != null)
            claims.put(Constants.CLAIM_USER_ROLE, role);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }
    public JwtTokenDto generateTokens(Long id, ERole role){
        return JwtTokenDto.of(
                generateToken(id, role, accessExpiration),
                generateToken(id, role, refreshExpiration),
                id
        );
    }

    public String generateRefreshToken(Long userId, ERole role) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh-Token", refreshToken);
    }

}
