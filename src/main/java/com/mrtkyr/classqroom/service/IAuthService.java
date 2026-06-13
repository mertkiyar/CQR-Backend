package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.iu.DtoRegisterRequestIU;
import com.mrtkyr.classqroom.jwt.AuthRequest;
import com.mrtkyr.classqroom.jwt.AuthResponse;

public interface IAuthService {
    DtoUser register(DtoRegisterRequestIU request);
    AuthResponse authenticate(AuthRequest request);
}
