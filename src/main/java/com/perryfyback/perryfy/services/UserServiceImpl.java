package com.perryfyback.perryfy.services;

import com.perryfyback.perryfy.entities.User;
import com.perryfyback.perryfy.models.UserRequest;
import com.perryfyback.perryfy.models.UserResponse;
import com.perryfyback.perryfy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(mapToUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        User user = new User();
        mapUserRequestToEntity(userRequest, user);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(mapToUserResponse(savedUser));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    mapUserRequestToEntity(userRequest, existingUser);
                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(mapToUserResponse(updatedUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUser_id(user.getUser_id());
        response.setName(user.getName());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setDefaultAddress(user.getDefault_address());
        response.setPaymentMethodToken(user.getPayment_method_token());
        return response;
    }

    private void mapUserRequestToEntity(UserRequest request, User user) {
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDefault_address(request.getDefaultAddress());
        user.setPayment_method_token(request.getPaymentMethodToken());
    }
} 