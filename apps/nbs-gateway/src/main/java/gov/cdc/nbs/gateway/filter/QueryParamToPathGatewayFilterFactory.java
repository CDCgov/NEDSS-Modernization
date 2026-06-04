package gov.cdc.nbs.gateway.filter;

import java.net.URI;
import java.util.List;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class QueryParamToPathGatewayFilterFactory
    extends AbstractGatewayFilterFactory<QueryParamToPathGatewayFilterFactory.Config> {

  public record Config(List<String> queryParamNames, String path) {}

  public QueryParamToPathGatewayFilterFactory() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      String path = config.path;
      for (String param : config.queryParamNames) {
        String paramValue = exchange.getRequest().getQueryParams().getFirst(param);

        if (paramValue != null) {
          // Replace placeholder (e.g., {id}) with the query param value
          path = path.replace("{" + param + "}", paramValue);
        }
      }

      URI mutatedUri =
          UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
              .replacePath(path)
              .replaceQueryParams(null)
              .build()
              .toUri();

      exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, mutatedUri);

      ServerHttpRequest request = exchange.getRequest().mutate().path(path).build();

      return chain.filter(exchange.mutate().request(request).build());
    };
  }
}
