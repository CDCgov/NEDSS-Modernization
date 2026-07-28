package gov.cdc.nbs.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class AccessLogFilter implements Filter {
  private static final String NBS_BASE_PATH = "/nbs/api";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest req
        && req.getRequestURI().startsWith(NBS_BASE_PATH)) {
      request.setAttribute("NBS_REQUEST", "true");
    }
    chain.doFilter(request, response);
  }
}
