package com.mrtkyr.classqroom.config;

import com.mrtkyr.classqroom.jwt.JwtAuthenticationFilter;
import com.mrtkyr.classqroom.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static final String AUTHENTICATE = "/authenticate";
    public static final String REGISTER = "/register";

    @Autowired
    private AuthenticationProvider authenticationProvider;

    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> request
                .requestMatchers(AUTHENTICATE, REGISTER)
                .permitAll()
                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
