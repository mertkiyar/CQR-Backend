package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.iu.DtoUserIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface IUserController {

    RootEntity<DtoUser> saveUser(DtoUserIU dtoUserIU);

    List<DtoUser> getAllUsers();

    RootEntity<DtoUser> getUserById(UUID id);

    void deleteUser(UUID id);

    RootEntity<DtoUser> updateUser(UUID id, DtoUserIU dtoUserIU);
}
