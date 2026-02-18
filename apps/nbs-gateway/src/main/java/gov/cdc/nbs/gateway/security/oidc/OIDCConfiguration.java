package gov.cdc.nbs.gateway.security.oidc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("oidc")
@ComponentScan({"gov.cdc.nbs.security.oidc"})
class OIDCConfiguration {}
