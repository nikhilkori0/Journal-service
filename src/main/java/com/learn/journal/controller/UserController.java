package com.learn.journal.controller;

import com.learn.journal.api.response.WeatherResponse;
import com.learn.journal.dto.UserDTO;
import com.learn.journal.entity.UserEntity;
import com.learn.journal.repository.UserRepository;
import com.learn.journal.service.UserService;
import com.learn.journal.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity userEntityInDB = userService.findByUserName(username);
        userEntityInDB.setUsername(userDTO.getUsername());
        userEntityInDB.setPassword(userDTO.getPassword());
        userService.saveNewUser(userEntityInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userRepository.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weather = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weather != null) {
            greeting = " Weather feels like " + weather.getCurrent().getFeelslike() + ".";
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + "." + greeting, HttpStatus.OK);
    }
}
