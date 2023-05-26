package gov.cdc.nbs.redirect.incoming;

import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authorization.NBSUserCookie;
import gov.cdc.nbs.authorization.SessionCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;

public sealed interface ClassicIncomingAuthorization {

    Consumer<HttpServletResponse> apply(final SecurityProperties properties);

    record Authorized(SessionCookie session, NBSUserCookie user) implements ClassicIncomingAuthorization {
        public Consumer<HttpServletResponse> apply(final SecurityProperties properties) {
            return outgoing -> {
                user.apply(properties, outgoing);
                session.apply(properties, outgoing);
            };
        }
    }


    record Unauthorized() implements ClassicIncomingAuthorization {

        public Consumer<HttpServletResponse> apply(final SecurityProperties properties) {
            return outgoing -> {
                outgoing.setStatus(HttpStatus.FOUND.value());
                outgoing.setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
            };
        }

    }
}
