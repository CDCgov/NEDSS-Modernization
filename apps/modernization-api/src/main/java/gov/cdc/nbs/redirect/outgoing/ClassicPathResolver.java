package gov.cdc.nbs.redirect.outgoing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ClassicPathResolver {

    private final UriComponentsBuilder base;

    public ClassicPathResolver(
        @Value("${nbs.wildfly.url:http://wildfly:7001}") final String url
    ) {
        this.base = UriComponentsBuilder.fromHttpUrl(url)
            .path("nbs/");
    }

    public URI base() {
        return base
            .build()
            .toUri();
    }

    public UriComponentsBuilder resolve(final String path) {
        return base.path(path);
    }

}
