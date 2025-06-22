package com.perryfyback.perryfy.services;

import com.perryfyback.perryfy.models.UserRequest;
import com.perryfyback.perryfy.models.UserResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserService {
    ResponseEntity<List<UserResponse>> getAllUsers();
    ResponseEntity<UserResponse> getUserById(Long id);
    ResponseEntity<UserResponse> createUser(UserRequest userRequest);
    ResponseEntity<UserResponse> updateUser(Long id, UserRequest userRequest);
    ResponseEntity<Void> deleteUser(Long id);
} 