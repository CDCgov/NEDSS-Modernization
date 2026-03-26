package gov.cdc.nbs.redirect.outgoing;

import java.io.IOException;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * A {@link ClientHttpRequestInterceptor} that adds basic logging to any requests made to the
 * classic NBS application to the {@code nbs.classic.outgoing} Logger.
 */
class ClassicOutgoingLoggingHttpRequestInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOG = LoggerFactory.getLogger("nbs.classic.outgoing");

  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
      throws IOException {

    ClientHttpResponse response = execution.execute(request, body);

    if (LOG.isDebugEnabled()) {

      URI uri = request.getURI();
      String path = uri.getRawPath();

      String content = new String(body);
      LOG.debug(
          "{} {}\tResponse: {}\n\n{}",
          request.getMethod().name(),
          path,
          response.getStatusCode(),
          content);
    }

    return response;
  }
}
