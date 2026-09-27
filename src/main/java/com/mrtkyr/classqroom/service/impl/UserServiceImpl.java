package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.iu.DtoUserIU;
import com.mrtkyr.classqroom.entity.User;
import com.mrtkyr.classqroom.jwt.JwtService;
import com.mrtkyr.classqroom.repository.UserRepository;
import com.mrtkyr.classqroom.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public List<DtoUser> getAllUsers() {
        List<DtoUser> dtoUserList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            dtoUserList.add(DtoUser.from(user));
        }
        return dtoUserList;
    }

    @Override
    public DtoUser getUserById(UUID id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.map(DtoUser::from).orElseGet(DtoUser::new);
    }

    @Override
    public void deleteUser(UUID id) {
        Optional<User> optUser = userRepository.findById(id);
        optUser.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public DtoUser updateUser(UUID id, DtoUserIU dtoUserIU) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setUserType(dtoUserIU.getUserType());
            User updatedUser = userRepository.save(user);
            return DtoUser.from(updatedUser);
        }
        return null;
    }

    @Override
    public DtoUser getUserByToken(String token) {
        String username = jwtService.getUsernameByToken(token);
        Optional<User> optUser = userRepository.findUserByEmail(username);
        return optUser.map(DtoUser::from).orElseGet(DtoUser::new);
    }
}
