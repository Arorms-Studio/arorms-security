package cn.arorms.framework.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String username;
    private String email;
//    private final Set<String> roles;
}
