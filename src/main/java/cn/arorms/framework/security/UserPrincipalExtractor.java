package cn.arorms.framework.security;

import org.springframework.security.oauth2.jwt.Jwt;


public class UserPrincipalExtractor implements JwtPrincipalExtractor<UserPrincipal> {
    @Override
    public UserPrincipal extract(Jwt jwt) {
        return new UserPrincipal(
                // uuid, username, email
                jwt.getSubject(),
                jwt.getClaimAsString("preferred_username"),
                jwt.getClaimAsString("email")
        );
    }

    @Override
    public String getPrincipalName(UserPrincipal principal) {
        return principal.getUsername();
    }
}
