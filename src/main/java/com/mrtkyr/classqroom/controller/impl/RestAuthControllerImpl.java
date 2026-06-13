package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IRestAuthController;
import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.jwt.AuthRequest;
import com.mrtkyr.classqroom.jwt.AuthResponse;
import com.mrtkyr.classqroom.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    @Override
    public DtoUser register(@RequestBody @Valid AuthRequest request) {
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    @Override
    public AuthResponse authenticate(@RequestBody @Valid AuthRequest request) {
        return authService.authenticate(request);
    }
}
