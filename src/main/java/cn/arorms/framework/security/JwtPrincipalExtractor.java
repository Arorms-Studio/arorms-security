package cn.arorms.framework.security;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtPrincipalExtractor<T> {

    T extract(Jwt jwt);

    String getPrincipalName(T principal);
}
