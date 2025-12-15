package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.model.Users;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user){
        Users createdUser = userService.createUser(user);

        if(createdUser == null){
            throw new UserAlreadyExistException(400, "A user with this email Already exists");
        }
        return ResponseEntity.status(201).body(user);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable long userId){
        boolean delValue = userService.deleteUser(userId);

        if(delValue){
            return ResponseEntity.status(204).body(true);   
        }

        return ResponseEntity.status(404).body(false);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable long userId){
        Users user = userService.getUserById(userId);

        if(user != null){
            return ResponseEntity.status(200).body(user);   
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> users = userService.getAllUsers();

        if(!users.isEmpty()){
            return ResponseEntity.status(200).body(users);   
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Users> updateUser(@RequestBody Users user , @PathVariable long userId){
        Users updatedUser = userService.updateUser(userId, user);

        if(updatedUser == null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(201).body(user);
    }

    
    

}
