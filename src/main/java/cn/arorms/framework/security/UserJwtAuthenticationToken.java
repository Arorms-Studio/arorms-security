package cn.arorms.framework.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Getter
public class UserJwtAuthenticationToken extends JwtAuthenticationToken {

    private final User user;

    public UserJwtAuthenticationToken(Jwt jwt,
                                      Collection<? extends GrantedAuthority> authorities,
                                      User user) {
        super(jwt, authorities, user.getUsername());
        this.user = user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}

