package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.iu.DtoRegisterRequestIU;
import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.entity.User;
import com.mrtkyr.classqroom.jwt.AuthRequest;
import com.mrtkyr.classqroom.jwt.AuthResponse;
import com.mrtkyr.classqroom.jwt.JwtService;
import com.mrtkyr.classqroom.repository.DepartmentRepository;
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
    private DepartmentRepository departmentRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public DtoUser register(DtoRegisterRequestIU request) {
        DtoUser dtoUser = new DtoUser();
        Department department = departmentRepository.findById((short) request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        user.setUserType(request.getUserType());
        user.setDepartment(department);
        user.setInCourse(false); // default value

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
