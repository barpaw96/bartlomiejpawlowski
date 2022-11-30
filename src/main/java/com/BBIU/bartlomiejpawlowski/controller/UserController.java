package com.BBIU.bartlomiejpawlowski.controller;

import com.BBIU.bartlomiejpawlowski.exception.UserNotFoundException;
import com.BBIU.bartlomiejpawlowski.model.User;
import com.BBIU.bartlomiejpawlowski.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BBIU.bartlomiejpawlowski.service.UserService;


import java.util.List;
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
    ResponseEntity<?> newUser(@RequestBody User newUser){
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Wrong email");
        }   else if (userRepository.findByUsername(newUser.getUsername()).isPresent()){
            return ResponseEntity.status(410).body("Wrong Username");
        }
        else {
            userRepository.save(newUser);
            return ResponseEntity.ok("Added");
        }
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
    ResponseEntity<?> updateUser(@RequestBody User newUser,@PathVariable Long id){
        Optional<User> user1 = userRepository.findByEmail(newUser.getEmail());
        if (user1.isPresent() && !user1.get().getId().equals(id)) {
            return ResponseEntity.status(409).body("Wrong email"); }

        user1 = userRepository.findByUsername(newUser.getUsername());
        if (user1.isPresent() && !user1.get().getId().equals(id)){
            return ResponseEntity.status(410).body("Wrong Username");
        }
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(newUser.getUsername());
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setComment(newUser.getComment());
            user.setSex(newUser.getSex());
            userRepository.save(user);
            return ResponseEntity.ok("Added");
        } else {
            return ResponseEntity.status(409).body("User not found");
        }
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " +id+ " has been deleted success.";
    }





}
