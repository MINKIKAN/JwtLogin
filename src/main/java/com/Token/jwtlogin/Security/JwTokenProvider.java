package com.Token.jwtlogin.Security;


import com.Token.jwtlogin.Entity.Authority;
import com.Token.jwtlogin.Service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@RequiredArgsConstructor
@Component
public class JwTokenProvider {

    @Value("${jwt.secret.key}")
    private  String salt;

    private Key secretKey;

    private final long exp = 1000L * 60 *60; //만료시간 1시간
    private final MemberDetailsService memberDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    //토큰 생성
    public String createToken(String login_id, List<Authority> roles) {
    Claims claims = Jwts.claims().setSubject(login_id);
    claims.put("roles", roles);
        Date now= new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    //권한정보 획득 Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token){
        UserDetails userDetails = memberDetailsService.loadUserByUsername(this.getloginId(token));
        return null;
    }
    public String getloginId(String token) {
        // 만료된 토큰에 대해 parseClaimsJws를 수행하면 io.jsonwebtoken.ExpiredJwtException이 발생한다.
        try {
            Jwts.parserBuilder().setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return e.getClaims()
                    .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
