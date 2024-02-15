package com.example.Eigar.controller;


import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.exception.UserServiceException;
import com.example.Eigar.model.EigarUser;
import com.example.Eigar.response.UserResponse;
import com.example.Eigar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long userId) {
        try {
            EigarUser user = userService.getUserById(userId);
            return ResponseEntity.ok(UserResponse.success(user));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UserResponse.notFound("User not found"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserResponse.error("Internal server error"));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<EigarUser>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }
    @PostMapping("register-new-user")
    public ResponseEntity<UserResponse> registerNewUser(@RequestBody EigarUser newUser){
        try {
            EigarUser registeredUser = userService.registerNewUser(newUser);
            return ResponseEntity.ok(UserResponse.success(registeredUser));
        } catch (UserServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UserResponse.error("Error registering new user"));
        }
    }
}

