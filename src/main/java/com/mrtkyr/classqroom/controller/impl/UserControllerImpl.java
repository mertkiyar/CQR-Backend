package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IUserController;
import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.DtoUserIU;
import com.mrtkyr.classqroom.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/user")
public class UserControllerImpl implements IUserController {

    @Autowired
    private IUserService userService;

    @PostMapping(path = "/save")
    @Override
    public DtoUser saveUser(@RequestBody @Valid DtoUserIU dtoUserIU) {
        return userService.saveUser(dtoUserIU);
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/get/{id}")
    @Override
    public DtoUser getUserById(@PathVariable(name = "id") UUID id) {
        return userService.getUserById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteUser(@PathVariable(name = "id") UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public DtoUser updateUser(@PathVariable(name = "id") UUID id,@RequestBody @Valid DtoUserIU dtoUserIU) {
        return userService.updateUser(id, dtoUserIU);
    }
}