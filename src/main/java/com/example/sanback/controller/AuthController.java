package com.example.sanback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sanback.service.UserService;
import com.example.sanback.dto.UserLoginRequest;
import com.example.sanback.dto.LoginResponse;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        if (userService.validateUser(loginRequest.getUserid(), loginRequest.getPassword())) {
            return new ResponseEntity<>(new LoginResponse("Login successful"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LoginResponse("Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }
    }
}
