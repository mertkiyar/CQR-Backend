package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.DtoUserIU;

import java.util.List;
import java.util.UUID;

public interface IUserController {

    DtoUser saveUser(DtoUserIU dtoUserIU);

    List<DtoUser> getAllUsers();

    DtoUser getUserById(UUID id);

    void deleteUser(UUID id);

    DtoUser updateUser(UUID id, DtoUserIU dtoUserIU);
}
