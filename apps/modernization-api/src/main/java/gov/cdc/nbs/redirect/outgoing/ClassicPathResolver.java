package gov.cdc.nbs.redirect.outgoing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ClassicPathResolver {

    private final String url;

    public ClassicPathResolver(
        @Value("${nbs.wildfly.url:http://wildfly:7001}") final String url
    ) {
        this.url = url;
    }

    public UriComponentsBuilder resolve(final String path) {
        return UriComponentsBuilder.fromHttpUrl(url).path(path);
    }

}
