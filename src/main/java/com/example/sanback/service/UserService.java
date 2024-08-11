package com.example.sanback.service;

import org.springframework.stereotype.Service;
import com.example.sanback.entity.User;
import com.example.sanback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateUser(String userid, String password) {
        User user = userRepository.findByUserid(userid);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}