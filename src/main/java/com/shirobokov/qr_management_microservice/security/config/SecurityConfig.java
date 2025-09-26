package com.shirobokov.qr_management_microservice.security.config;


import com.shirobokov.qr_management_microservice.security.jwt.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity(debug = true)
@Slf4j
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(csrf->csrf.disable())
                .formLogin(form->form.disable())
                .logout(logout -> logout.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session.disable())
                .anonymous(anonymous -> anonymous.disable())
                .requestCache(cache -> cache.disable())
                .headers(headers -> headers.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/private").hasRole("USER")
                        .anyRequest().permitAll()
                );
        http.addFilterAfter(jwtAuthenticationFilter, SecurityContextHolderFilter.class);

        return http.build();
    }



}
