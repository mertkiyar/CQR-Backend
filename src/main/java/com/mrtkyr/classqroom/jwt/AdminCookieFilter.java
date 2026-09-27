package com.mrtkyr.classqroom.jwt;

import com.mrtkyr.classqroom.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class AdminCookieFilter extends OncePerRequestFilter {
    public static final String COOKIE = "cqr_admin";
    private final JwtService jwtService;
    private final UserDetailsService users;

    public AdminCookieFilter(JwtService jwtService, UserDetailsService users) {
        this.jwtService = jwtService;
        this.users = users;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        if (request.getCookies() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = Arrays.stream(request.getCookies()).filter(c -> COOKIE.equals(c.getName()))
                    .map(Cookie::getValue).findFirst().orElse(null);
            if (token != null) {
                try {
                    String username = jwtService.getUsernameByToken(token);
                    User user = (User) users.loadUserByUsername(username);
                    if (!jwtService.isTokenExpired(token) && user.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        // Cookie authentication is restricted to the admin UI and requests explicitly made by it.
                        if (request.getRequestURI().startsWith("/admin/") || "1".equals(request.getHeader("X-Admin-Request"))) {
                            if (unsafe(request) && !sameOrigin(request)) {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                                return;
                            }
                            SecurityContextHolder.getContext().setAuthentication(
                                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
                        }
                    }
                } catch (Exception ignored) {
                    // Expired or invalid cookie leads to the normal 401 response.
                }
            }
        }
        chain.doFilter(request, response);
    }

    public static boolean unsafe(HttpServletRequest request) {
        return !request.getMethod().equals("GET") && !request.getMethod().equals("HEAD")
                && !request.getMethod().equals("OPTIONS");
    }

    public static boolean sameOrigin(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        if (origin == null) return false;
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null) scheme = request.getScheme();
        return origin.equals(scheme + "://" + request.getHeader("Host"));
    }
}
