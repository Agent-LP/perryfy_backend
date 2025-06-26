package com.perryfyback.perryfy.services.User;

import org.springframework.http.ResponseEntity;

import com.perryfyback.perryfy.models.users.UserRequest;
import com.perryfyback.perryfy.models.users.UserResponse;

import java.util.List;

public interface UserService {
    ResponseEntity<List<UserResponse>> getAllUsers();
    ResponseEntity<UserResponse> getUserById(int id);
    ResponseEntity<UserResponse> createUser(UserRequest userRequest);
    ResponseEntity<UserResponse> updateUser(int id, UserRequest userRequest);
    ResponseEntity<Void> deleteUser(int id);
} 