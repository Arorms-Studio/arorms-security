package cn.arorms.framework.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

import java.util.List;

@Configuration
public class SecurityAutoConfiguration {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${arorms.security.jwt.audience}")
    private String requiredAudience;

    @Bean
    @ConditionalOnMissingBean
    public JwtPrincipalExtractor<UserPrincipal> defaultPrincipalExtractor() {
        return new UserPrincipalExtractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(requiredAudience);
//        OAuth2TokenValidator<Jwt> withAudience = new JwtClaimValidator<List<String>>(
//                JwtClaimNames.AUD,
//                aud -> aud != null && aud.contains(requiredAudience)
//        );
        OAuth2TokenValidator<Jwt> combinedValidator = new DelegatingOAuth2TokenValidator<>(withIssuer, withAudience);
        jwtDecoder.setJwtValidator(combinedValidator);
        return jwtDecoder;
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> KeycloakAuthenticationConverter<T> keycloakAuthenticationConverter(JwtPrincipalExtractor<T> extractor) {
        return new KeycloakAuthenticationConverter<>(
                extractor::extract,
                extractor::getPrincipalName
        );
    }
}
