package com.example.Eigar.controller;


import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.exception.UserServiceException;
import com.example.Eigar.model.EigarUser;
import com.example.Eigar.model.Owner;
import com.example.Eigar.model.Renter;
import com.example.Eigar.model.UserType;
import com.example.Eigar.response.UserResponse;
import com.example.Eigar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<EigarUser>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/register-new-renter")
    public ResponseEntity<UserResponse> registerNewRenter(@RequestBody Renter newRenter) {
        try {
            Renter registeredRenter = userService.registerNewUser(newRenter, UserType.RENTER);
            return ResponseEntity.ok(UserResponse.success(registeredRenter));
        } catch (UserServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UserResponse.error("Error registering new RENTER user"));
        }
    }

    @PostMapping("/register-new-owner")
    public ResponseEntity<UserResponse> registerNewOwner(@RequestBody Owner newOwner) {
        try {
            Owner registeredOwner = userService.registerNewUser(newOwner, UserType.OWNER);
            return ResponseEntity.ok(UserResponse.success(registeredOwner));
        } catch (UserServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UserResponse.error("Error registering new OWNER user"));
        }
    }
}