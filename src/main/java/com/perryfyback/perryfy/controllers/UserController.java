package com.perryfyback.perryfy.controllers;

import com.perryfyback.perryfy.models.users.UserRequest;
import com.perryfyback.perryfy.models.users.UserResponse;
import com.perryfyback.perryfy.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('MERCHANDISER')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MERCHANDISER') or #id == authentication.principal.userId")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('MERCHANDISER')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MERCHANDISER') or #id == authentication.principal.userId")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MERCHANDISER')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
} 