package gov.cdc.nbs.redirect.outgoing;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class ClassicOutgoingAuthenticationRequestInterceptor implements ClientHttpRequestInterceptor {

    private final ClassicContext context;

    ClassicOutgoingAuthenticationRequestInterceptor(final ClassicContext context) {
        this.context = context;
    }

    @Override
    public ClientHttpResponse intercept(
        final HttpRequest request,
        final byte[] body,
        final ClientHttpRequestExecution execution
    )
        throws IOException {
        request.getHeaders().add(HttpHeaders.COOKIE, resolveSessionCookie(context));
        return execution.execute(request, body);
    }

    private String resolveSessionCookie(final ClassicContext context) {
        return "JSESSIONID=" + context.session() + ";";
    }
}
