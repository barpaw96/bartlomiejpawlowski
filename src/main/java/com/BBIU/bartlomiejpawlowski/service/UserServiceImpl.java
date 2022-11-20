package com.BBIU.bartlomiejpawlowski.service;

import com.BBIU.bartlomiejpawlowski.model.User;
import com.BBIU.bartlomiejpawlowski.repository.UserRepository;
import com.BBIU.bartlomiejpawlowski.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users;
    }




}
