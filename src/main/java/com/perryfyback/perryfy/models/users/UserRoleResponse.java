package com.perryfyback.perryfy.models.users;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {
    Integer roleId;
    String role;
}
