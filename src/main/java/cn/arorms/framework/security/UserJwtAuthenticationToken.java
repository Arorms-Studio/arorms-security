package cn.arorms.framework.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Getter
public class UserJwtAuthenticationToken<T> extends JwtAuthenticationToken {

    private final T userPrincipal;

    public UserJwtAuthenticationToken(Jwt jwt,
                                      Collection<? extends GrantedAuthority> authorities,
                                      T userPrincipal,
                                      String name) {
        super(jwt, authorities, name);
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Object getPrincipal() {
        return userPrincipal;
    }
}

