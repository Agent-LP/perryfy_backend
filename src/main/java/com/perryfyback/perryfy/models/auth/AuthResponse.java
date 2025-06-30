package com.perryfyback.perryfy.models.auth;

import java.util.List;


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
  String userName;
  List<UserRoleResponse> userRoles;
  String token;

}