package com.BBIU.bartlomiejpawlowski.controller;

import com.BBIU.bartlomiejpawlowski.exception.UserNotFoundException;
import com.BBIU.bartlomiejpawlowski.model.User;
import com.BBIU.bartlomiejpawlowski.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BBIU.bartlomiejpawlowski.service.UserService;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam("query") String query){
        return ResponseEntity.ok(userService.searchUser(query));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser,@PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setComment(newUser.getComment());
                    user.setSex(newUser.getSex());
                    return userRepository.save(user);
                }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " +id+ " has been deleted success.";
    }


    @PostMapping("/create")
    public Map<String, Object> create(@Valid @RequestBody User data){

        HashMap<String, Object> response = new HashMap<String, Object>();

        try {

            Optional<User> validEmail = userRepository.findByEmail(data.getEmail());

            if(validEmail.isPresent()) {
                response.put("message", "The email "+data.getEmail()+" is already registered ");
                response.put("success", false);
                return response;
            }
            else {
                userRepository.save(data);
                response.put("message", "Successful save");
                response.put("success", true);
                return response;
            }


        } catch (Exception e) {
            // TODO: handle exception
            response.put("message", e.getMessage());
            response.put("success",false);
            return response;
        }

    }
}
