package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoUser;
import com.mrtkyr.classqroom.dto.iu.DtoUserIU;
import com.mrtkyr.classqroom.entity.User;
import com.mrtkyr.classqroom.repository.UserRepository;
import com.mrtkyr.classqroom.service.IUserService;
import org.springframework.beans.BeanUtils;
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

    @Override
    public DtoUser saveUser(DtoUserIU dtoUserIU) {
        User user = new User();
        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(dtoUserIU, user);
        user = userRepository.save(user);
        BeanUtils.copyProperties(user, dtoUser);
        return dtoUser;
    }

    @Override
    public List<DtoUser> getAllUsers() {
        List<DtoUser> dtoUserList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            DtoUser dtoUser = new DtoUser();
            BeanUtils.copyProperties(user, dtoUser);
            dtoUserList.add(dtoUser);
        }
        return dtoUserList;
    }

    @Override
    public DtoUser getUserById(UUID id) {
        DtoUser dtoUser = new DtoUser();
        Optional<User> optUser = userRepository.findById(id);
        optUser.ifPresent(user -> BeanUtils.copyProperties(user, dtoUser));
        return dtoUser;
    }

    @Override
    public void deleteUser(UUID id) {
        Optional<User> optUser = userRepository.findById(id);
        optUser.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public DtoUser updateUser(UUID id, DtoUserIU dtoUserIU) {
        DtoUser dtoUser = new DtoUser();
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setUserType(dtoUserIU.getUserType());
            User updatedUser = userRepository.save(user);
            BeanUtils.copyProperties(updatedUser, dtoUser);
            return dtoUser;
        }
        return null;
    }
}
