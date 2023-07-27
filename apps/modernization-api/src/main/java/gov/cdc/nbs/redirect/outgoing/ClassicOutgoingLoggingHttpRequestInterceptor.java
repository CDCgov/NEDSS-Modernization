package gov.cdc.nbs.redirect.outgoing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A {@link ClientHttpRequestInterceptor} that adds basic logging to any requests made to the classic NBS application to
 * the {@code nbs.classic.outgoing} Logger.
 */
@Component
class ClassicOutgoingLoggingHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger("nbs.classic.outgoing");

    private final ClassicContext context;

    ClassicOutgoingLoggingHttpRequestInterceptor(final ClassicContext context) {
        this.context = context;
    }

    @Override
    public ClientHttpResponse intercept(
        final HttpRequest request,
        final byte[] body,
        final ClientHttpRequestExecution execution
    ) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);

        if (LOG.isDebugEnabled()) {

            String host = request.getURI().getHost();
            String path = request.getURI().getRawPath();

            LOG.debug(
                "\n\t{}\n\t{} {} {}\n\tHeaders:\n\t\t{}\n\tResponse: {}\n\tHeaders:\n\t\t{}",
                context,
                host,
                request.getMethodValue(),
                path,
                request.getHeaders(),
                response.getStatusCode(),
                response.getHeaders()
            );
        }

        return response;
    }


}
