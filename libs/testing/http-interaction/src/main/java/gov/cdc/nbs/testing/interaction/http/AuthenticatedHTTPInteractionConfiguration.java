package gov.cdc.nbs.testing.interaction.http;

import gov.cdc.nbs.testing.authorization.EnableAuthenticationSupport;
import gov.cdc.nbs.testing.identity.EnableSequentialIdentityGeneration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableSequentialIdentityGeneration
@EnableAuthenticationSupport
class AuthenticatedHTTPInteractionConfiguration {}
