package gov.cdc.nbs.authentication.session;

import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import gov.cdc.nbs.authentication.NBSUserCookie;
import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.authentication.config.SecurityProperties;


public sealed interface SessionAuthorization {

  Consumer<HttpServletResponse> apply(final SecurityProperties properties);

  record Authorized(SessionCookie session, NBSUserCookie user) implements SessionAuthorization {
    public Consumer<HttpServletResponse> apply(final SecurityProperties properties) {
      return outgoing -> {
        user.apply(properties, outgoing);
        session.apply(properties, outgoing);
      };
    }
  }


  record Unauthorized() implements SessionAuthorization {

    public Consumer<HttpServletResponse> apply(final SecurityProperties properties) {
      return outgoing -> {
        outgoing.setStatus(HttpStatus.FOUND.value());
        outgoing.setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
      };
    }

  }
}

