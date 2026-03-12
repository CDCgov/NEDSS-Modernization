package gov.cdc.nbs.redirect.outgoing;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
class ClassicContextConfiguration {

  @Bean
  @RequestScope
  ClassicContext classicContext(final HttpServletRequest request) {
    String session = resolveSessionIdentifier(request.getCookies());
    return new DefaultClassicContext(session);
  }

  private String resolveSessionIdentifier(final Cookie[] cookies) {
    if (cookies == null) {
      return null;
    }
    for (var cookie : cookies) {
      if (cookie.getName().equals("JSESSIONID")) {
        return cookie.getName() + "=" + cookie.getValue() + "; ";
      }
    }
    return null;
  }
}
