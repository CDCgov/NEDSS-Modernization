package gov.cdc.nbs.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.stereotype.Component;

/**
 * The AccessLogFilter class allows for custom filterability against our Tomcat HTTP access logs
 * (assuming they're enabled), and what gets included/excluded in said logs. Attributes can be set
 * on subsets of requests given some set of criteria, which can then be used to opt those requests
 * in or out of our access logs.<br>
 * <br>
 * See {@code accesslog} in {@code application.yml} for example usage.
 */
@Component
public class AccessLogFilter implements Filter {
  private static final Collection<String> FILE_EXTENSIONS =
      Arrays.asList(".js", ".css", ".jpg", ".jpeg", ".gif", ".ico");

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest req) {
      // Tag all static assets
      if (req.getRequestURI().startsWith("/static")
          || FILE_EXTENSIONS.stream().anyMatch(suffix -> req.getRequestURI().endsWith(suffix))) {
        request.setAttribute("STATIC_ASSET", "true");
      }

      //  Tag all reports requests
      if (req.getRequestURI().startsWith("/nbs/api/reports")) {
        request.setAttribute("NBS_REPORTS", "true");
      }
    }
    chain.doFilter(request, response);
  }
}
