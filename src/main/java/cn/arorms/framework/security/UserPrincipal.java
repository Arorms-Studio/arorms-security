package cn.arorms.framework.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal {
    private String id;
    private String username;
    private String email;
//    private final Set<String> roles;
}
