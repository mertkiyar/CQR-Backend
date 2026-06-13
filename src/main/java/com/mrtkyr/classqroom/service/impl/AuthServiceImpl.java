package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.entity.User;
import com.mrtkyr.classqroom.jwt.AuthRequest;
import com.mrtkyr.classqroom.jwt.AuthResponse;
import com.mrtkyr.classqroom.jwt.JwtService;
import com.mrtkyr.classqroom.repository.UserRepository;
import com.mrtkyr.classqroom.service.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public DtoUser register(AuthRequest request) {
        DtoUser dtoUser = new DtoUser();
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dtoUser);
        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())); //verify user
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

}
