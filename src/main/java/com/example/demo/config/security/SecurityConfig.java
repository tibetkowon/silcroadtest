package com.example.demo.config.security;

import com.example.demo.config.jwt.JwtAccessDeniedHandler;
import com.example.demo.config.jwt.JwtAuthenticationEntryPoint;
import com.example.demo.config.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//Spring Security의 웹 보안 지원 활성화, Spring MVC와 통합 제공
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenFilter jwtRequestFilter;

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //CSRF(Cross-Site Request Forgery) 보호 비활성화
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(Customizer.withDefaults());

        //HTTP 기본 인증 비활성화
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);

        httpSecurity.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        //세션 관리 상태 없음으로 구성, Spring Security가 세션 생성 or 사용 X
        httpSecurity.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

         //권한 규칙 구성 시작
         httpSecurity.authorizeHttpRequests(
                 authorize -> authorize
                         .requestMatchers("/sign-in", "/sign-up", "/refresh").permitAll()
                         .anyRequest().authenticated()
         );

        //JwtTokenFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

         httpSecurity.exceptionHandling((exceptionHandling) -> exceptionHandling
                 .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                 .accessDeniedHandler(jwtAccessDeniedHandler)
         );

         return httpSecurity.build();
     }
}