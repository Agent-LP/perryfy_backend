package com.perryfyback.perryfy.services.User;

import com.perryfyback.perryfy.entities.User;
import com.perryfyback.perryfy.models.users.UserRequest;
import com.perryfyback.perryfy.models.users.UserResponse;
import com.perryfyback.perryfy.repositories.UserRepository;
import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StripeUserService stripeUserService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(int id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(mapToUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        try {
            // Create user in database
            User user = new User();
            mapUserRequestToEntity(userRequest, user);
            
            // Create customer in Stripe
            var stripeCustomer = stripeUserService.createStripeCustomer(
                userRequest.getEmail(), 
                userRequest.getName() + " " + userRequest.getLastname()
            );
            
            // Create payment method in Stripe
            var paymentMethod = stripeUserService.createPaymentMethod();
            
            // Attach payment method to customer
            stripeUserService.attachPaymentMethodToCustomer(
                paymentMethod.getId(), 
                stripeCustomer.getId()
            );
            
            // Save Stripe IDs to user
            user.setPaymentMethodToken(paymentMethod.getId());
            
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(mapToUserResponse(savedUser));
            
        } catch (StripeException e) {
            System.err.println("Error creating user in Stripe: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponse> updateUser(int id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    try {
                        mapUserRequestToEntity(userRequest, existingUser);
                        User updatedUser = userRepository.save(existingUser);
                        return ResponseEntity.ok(mapToUserResponse(updatedUser));
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().<UserResponse>build();
                    }
                })
                .orElse(ResponseEntity.notFound().<UserResponse>build());
    }

    @Override
    public ResponseEntity<Void> deleteUser(int id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setDefaultAddress(user.getDefaultAddress());
        response.setPaymentMethodToken(user.getPaymentMethodToken());
        return response;
    }

    private void mapUserRequestToEntity(UserRequest request, User user) {
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDefaultAddress(request.getDefaultAddress());
        user.setPaymentMethodToken(request.getPaymentMethodToken());
    }
} 