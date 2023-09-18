package gov.cdc.nbs.authentication.token;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class NBSTokenCookieEnsurer {

    private final TokenCreator creator;
    private final SecurityProperties securityProperties;

    NBSTokenCookieEnsurer(
        final TokenCreator creator,
        final SecurityProperties securityProperties
    ) {
        this.creator = creator;
        this.securityProperties = securityProperties;
    }

    public void ensure(
        final String username,
        final HttpServletResponse outgoing
    ) {
        NBSToken token = creator.forUser(username);

        token.apply(securityProperties, outgoing);
    }
}
