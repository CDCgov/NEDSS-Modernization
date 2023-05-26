package gov.cdc.nbs.authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        gov.cdc.nbs.authentication.UserService.class,
        gov.cdc.nbs.authentication.config.JWTSecurityConfig.class,
        gov.cdc.nbs.authentication.JWTFilter.class,
        gov.cdc.nbs.authentication.UserPermissionFinder.class,
        gov.cdc.nbs.authentication.UserDetailsProvider.class
})
public class AuthenticationConfig {

}
