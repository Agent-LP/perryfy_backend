package com.perryfyback.perryfy.models.auth;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.perryfyback.perryfy.models.users.UserRoleResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor  
public class AuthResponse {
  Integer userId;
  List<UserRoleResponse> userRoles;
  String token;

}