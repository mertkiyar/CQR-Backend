package com.mrtkyr.classqroom.config;

import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.jwt.JwtAuthenticationFilter;
import com.mrtkyr.classqroom.jwt.JwtService;
import com.mrtkyr.classqroom.jwt.AdminCookieFilter;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static final String AUTHENTICATE = "/authenticate";
    public static final String REGISTER = "/register";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String API_DOCS = "/v3/api-docs/**";

    @Autowired
    private AuthenticationProvider authenticationProvider;

    private JwtAuthenticationFilter authenticationFilter;
    private final AdminCookieFilter adminCookieFilter;

    @Autowired
    private JsonMapper jsonMapper;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtService jwtService, UserDetailsService userDetailsService, AdminCookieFilter adminCookieFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        this.adminCookieFilter = adminCookieFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .requestMatchers(AUTHENTICATE, REGISTER, "/admin", "/admin/", "/admin/index.html", "/admin/app.js", "/admin/style.css", "/admin/logo.png", "/admin/login", SWAGGER_UI, API_DOCS)
                .permitAll()
                .requestMatchers(GET, "/users/me", "/students/*", "/lecturers/*", "/lecturer-courses/lecturers/*/courses", "/attendance-records/students/*", "/attendance-records/lecturers/*", "/terms", "/terms/*").authenticated()
                .requestMatchers("/admin/**", "/languages", "/languages/**", "/faculties", "/faculties/**",
                        "/departments", "/departments/**", "/courses", "/courses/**", "/students", "/students/**",
                        "/lecturers", "/lecturers/**", "/student-courses", "/student-courses/**",
                        "/lecturer-courses", "/lecturer-courses/**", "/users", "/users/**", "/terms", "/terms/**" )
                .hasRole("ADMIN")
                .requestMatchers(POST, "/attendances").hasAnyRole("ADMIN", "LECTURER")
                .requestMatchers(GET, "/attendances").hasRole("ADMIN")
                .requestMatchers(GET, "/attendance-records").hasRole("ADMIN")
                .requestMatchers(POST, "/attendance-records").hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers(GET, "/attendance-records/*").hasRole("ADMIN")
                .requestMatchers(POST, "/attendance-sessions/**").hasRole("ADMIN")
                .requestMatchers(PUT, "/attendance-sessions/**").hasRole("ADMIN")
                .requestMatchers(DELETE, "/attendance-sessions/**").hasRole("ADMIN")
                .requestMatchers(PUT, "/attendances/**", "/attendance-records/**").hasRole("ADMIN")
                .requestMatchers(DELETE, "/attendances/**", "/attendance-records/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, exception) -> {
                            boolean hasToken = request.getHeader("Authorization") != null;
                            MessageType type = hasToken ? MessageType.INVALID_TOKEN : MessageType.UNAUTHORIZED;
                            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, type);
                        })
                        .accessDeniedHandler((request, response, exception) ->
                                writeError(response, HttpServletResponse.SC_FORBIDDEN, MessageType.FORBIDDEN)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(adminCookieFilter, JwtAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public FilterRegistrationBean<AdminCookieFilter> adminCookieFilterRegistration(AdminCookieFilter filter) {
        FilterRegistrationBean<AdminCookieFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    private void writeError(HttpServletResponse response, int status, MessageType type) throws java.io.IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        jsonMapper.writeValue(response.getOutputStream(), RootEntity.error(type.getCode(), type.getMessage()));
    }
}
