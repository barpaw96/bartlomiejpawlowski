package com.BBIU.bartlomiejpawlowski.service;

import com.BBIU.bartlomiejpawlowski.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> searchUser(String query);


}
