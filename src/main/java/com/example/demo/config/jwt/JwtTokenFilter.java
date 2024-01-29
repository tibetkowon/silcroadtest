package com.example.demo.config.jwt;

import com.example.demo.config.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String authToken = Arrays.stream(cookies)
                    .filter(cookie -> "authToken".equals(cookie.getName()))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("인증정보가 없습니다."))
                    .getValue();


            if (jwtProvider.isNotExpired(authToken)) {
                Long userId = jwtProvider.getUserId(authToken);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    throw new IllegalArgumentException("해당하는 유저가 업습니다.");
                }
            } else {
                throw new BadCredentialsException("유효하지 않은 토큰입니다.");
            }
        }
        filterChain.doFilter(request, response);
    }
}