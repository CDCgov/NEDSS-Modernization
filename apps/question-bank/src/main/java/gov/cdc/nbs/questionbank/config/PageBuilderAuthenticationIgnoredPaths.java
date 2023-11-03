package gov.cdc.nbs.questionbank.config;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.authentication.NBSAuthenticationFilter.IgnoredPaths;

@Component
public class PageBuilderAuthenticationIgnoredPaths implements IgnoredPaths {

  @Override
  public Collection<String> get() {
    return Arrays.asList(
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v2/api-docs/**");
  }

  public String[] asArray() {
    return get().toArray(new String[] {});
  }

}
