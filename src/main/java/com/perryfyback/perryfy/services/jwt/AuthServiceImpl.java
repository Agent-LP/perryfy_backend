package com.perryfyback.perryfy.services.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perryfyback.perryfy.entities.User;
import com.perryfyback.perryfy.entities.Role;
import com.perryfyback.perryfy.models.auth.AuthResponse;
import com.perryfyback.perryfy.models.auth.LoginRequest;
import com.perryfyback.perryfy.models.users.UserRequest;
import com.perryfyback.perryfy.models.users.UserRoleResponse;
import com.perryfyback.perryfy.repositories.UserRepository;
import com.perryfyback.perryfy.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


   

    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .userId(user.getUserId())
            .userRoles(user.getRoles().stream().map(role -> new UserRoleResponse(role.getRole_id(), role.getRole())).toList())
            .token(token)
            .build();
    }

    @Transactional
    public AuthResponse register(UserRequest request) {
        // Get customer role by default
        Role customerRole = roleRepository.findByRole("customer")
            .orElseThrow(() -> new RuntimeException("Customer role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        User user = User.builder()
            .name(request.getName())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .defaultAddress(request.getDefaultAddress())
            .roles(roles)
            .build();

        userRepository.save(user);
        

        return AuthResponse.builder()
            .userId(user.getUserId())
            .userRoles(user.getRoles().stream().map(role -> new UserRoleResponse(role.getRole_id(), role.getRole())).toList())
            .token(jwtService.getToken(user))
            .build();
    }
}