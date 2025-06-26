package com.perryfyback.perryfy.services.jwt;

import com.perryfyback.perryfy.models.auth.AuthResponse;
import com.perryfyback.perryfy.models.auth.LoginRequest;
import com.perryfyback.perryfy.models.users.UserRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(UserRequest request);

}