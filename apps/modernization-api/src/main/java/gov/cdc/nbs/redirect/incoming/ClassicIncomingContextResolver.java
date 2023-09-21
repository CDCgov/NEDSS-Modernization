package gov.cdc.nbs.redirect.incoming;

import gov.cdc.nbs.authentication.AuthorizedUserResolver;
import gov.cdc.nbs.authentication.NBSUserCookie;
import gov.cdc.nbs.authentication.SessionCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
class ClassicIncomingContextResolver {

    private final AuthorizedUserResolver resolver;

    ClassicIncomingContextResolver(final AuthorizedUserResolver resolver) {
        this.resolver = resolver;
    }

    ClassicIncomingAuthorization resolve(final HttpServletRequest incoming) {
        return SessionCookie.resolve(incoming.getCookies())
            .flatMap(
                session -> resolver.resolve(session.identifier())
                    .map(user -> authorized(session, new NBSUserCookie(user)))
            ).orElse(new ClassicIncomingAuthorization.Unauthorized());
    }

    private ClassicIncomingAuthorization authorized(final SessionCookie session, final NBSUserCookie user) {
        return new ClassicIncomingAuthorization.Authorized(session, user);
    }

}
