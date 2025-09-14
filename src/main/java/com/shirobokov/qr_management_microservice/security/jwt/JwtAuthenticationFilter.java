package com.shirobokov.qr_management_microservice.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shirobokov.qr_management_microservice.security.jwt.error.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationDeserializer jwtAuthenticationDeserializer;

    public JwtAuthenticationFilter(JwtAuthenticationDeserializer jwtAuthenticationDeserializer) {
        this.jwtAuthenticationDeserializer = jwtAuthenticationDeserializer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtAuthenticationDeserializer.getClaimsFromToken(token);



            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            handleException(response, e.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", message);
        errorDetails.put("status", 401);
        errorDetails.put("timestamp", System.currentTimeMillis());

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
}
