package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IUserController;
import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.DtoUserIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/user")
public class UserControllerImpl extends RestBaseController implements IUserController {

    @Autowired
    private IUserService userService;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoUser> saveUser(@RequestBody @Valid DtoUserIU dtoUserIU) {
        return ok(userService.saveUser(dtoUserIU));
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/get/{id}")
    @Override
    public RootEntity<DtoUser> getUserById(@PathVariable(name = "id") UUID id) {
        return ok(userService.getUserById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteUser(@PathVariable(name = "id") UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoUser> updateUser(@PathVariable(name = "id") UUID id,@RequestBody @Valid DtoUserIU dtoUserIU) {
        return ok(userService.updateUser(id, dtoUserIU));
    }
}