package cn.arorms.framework.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KeycloakAuthenticationConverter<T> implements Converter<Jwt, AbstractAuthenticationToken> {

    private final Function<Jwt, T> principalMapper;

    private final Function<T, String> nameExtractor;

    public KeycloakAuthenticationConverter(Function<Jwt, T> principalMapper, Function<T, String> nameExtractor) {
        this.principalMapper = principalMapper;
        this.nameExtractor = nameExtractor;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        List<String> roles = Collections.emptyList();
        if (realmAccess != null) {
            Object rawRoles = realmAccess.get("roles");
            if (rawRoles instanceof List<?> list) {
                roles = list.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
            }
        }

        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

        T principal = principalMapper.apply(jwt);
        String principalName = nameExtractor.apply(principal);

        return new UserJwtAuthenticationToken<>(jwt, authorities, principal, principalName);
    }
}
