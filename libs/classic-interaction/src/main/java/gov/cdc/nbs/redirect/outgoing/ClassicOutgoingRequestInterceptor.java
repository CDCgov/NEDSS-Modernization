package gov.cdc.nbs.redirect.outgoing;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
class ClassicOutgoingRequestInterceptor implements ClientHttpRequestInterceptor {

  private final ClassicContext context;

  ClassicOutgoingRequestInterceptor(final ClassicContext context) {
    this.context = context;
  }

  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
      throws IOException {
    request.getHeaders().add(HttpHeaders.COOKIE, context.session());
    return execution.execute(request, body);
  }
}
