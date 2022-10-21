package gov.cdc.nbs.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConstructorBinding
@ConfigurationProperties(prefix = "nbs.security")
@Getter
@RequiredArgsConstructor
public class SecurityProperties {
    private final String tokenSecret;
    private final String tokenIssuer;
    private final long tokenExpirationMillis;
}
