package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.entity.User;
import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.iu.DtoRegisterRequestIU;
import com.mrtkyr.classqroom.jwt.AdminCookieFilter;
import com.mrtkyr.classqroom.jwt.AuthRequest;
import com.mrtkyr.classqroom.jwt.JwtService;
import com.mrtkyr.classqroom.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminAuthController {
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final IAuthService authService;

    public AdminAuthController(AuthenticationManager manager, JwtService jwtService, IAuthService authService) {
        this.manager = manager;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<RootEntity<String>> login(@RequestBody @Valid AuthRequest credentials,
                                                    HttpServletRequest request) {
        if (!AdminCookieFilter.sameOrigin(request) || !secureTransport(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RootEntity.error("Secure same-origin request required"));
        }
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getEmail(), credentials.getPassword()));
        User user = (User) authentication.getPrincipal();
        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RootEntity.error("Admin access required"));
        }
        String cookie = AdminCookieFilter.COOKIE + "=" + jwtService.generateToken(user)
                + "; Path=/; Max-Age=259200; HttpOnly; SameSite=Strict" + (https(request) ? "; Secure" : "");
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).body(RootEntity.ok("Login successful"));
    }

    @GetMapping({"/admin", "/admin/"})
    public ResponseEntity<Void> index() {
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/admin/index.html").build();
    }

    @GetMapping("/admin/session")
    public RootEntity<String> session() { return RootEntity.ok("Active"); }

    @PostMapping("/admin/register")
    public RootEntity<DtoUser> register(@RequestBody @Valid DtoRegisterRequestIU request) {
        return RootEntity.ok(authService.register(request));
    }

    @PostMapping("/admin/logout")
    public ResponseEntity<RootEntity<String>> logout(HttpServletRequest request) {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                AdminCookieFilter.COOKIE + "=; Path=/; Max-Age=0; HttpOnly; SameSite=Strict" + (https(request) ? "; Secure" : ""))
                .body(RootEntity.ok("Logout successful"));
    }

    private boolean secureTransport(HttpServletRequest request) {
        return https(request) ||
                ("http".equals(request.getScheme()) && ("localhost".equals(request.getServerName()) ||
                        "127.0.0.1".equals(request.getServerName())));
    }

    private boolean https(HttpServletRequest request) {
        return "https".equals(request.getHeader("X-Forwarded-Proto")) || request.isSecure();
    }
}
