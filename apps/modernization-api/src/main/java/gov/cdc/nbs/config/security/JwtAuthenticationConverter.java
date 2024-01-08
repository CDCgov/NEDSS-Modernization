package gov.cdc.nbs.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserService;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Autowired
    UserService userService;

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        String username = source.getClaim("preferred_username");
        NbsUserDetails userDetails = userService.loadUserByUsername(username);
        return new PreAuthenticatedAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());

    }
}
