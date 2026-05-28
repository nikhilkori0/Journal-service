package com.learn.journal.controller;

import com.learn.journal.dto.UserDTO;
import com.learn.journal.entity.User;
import com.learn.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserDTO userDTO) {
        User user = User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).build();
        userService.saveNewUser(user);
    }
}
