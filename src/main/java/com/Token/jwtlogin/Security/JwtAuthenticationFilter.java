package com.Token.jwtlogin.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwTokenProvider jwTokenProvider;

    public JwtAuthenticationFilter(JwTokenProvider jwTokenProvider){
        this.jwTokenProvider=jwTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwTokenProvider.resolveToken(request);

        if (token !=null && jwTokenProvider.validateToken(token)){
            // 엑세스 토큰 체크
            token = token.split(" ")[1].trim();
            Authentication auth = jwTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
