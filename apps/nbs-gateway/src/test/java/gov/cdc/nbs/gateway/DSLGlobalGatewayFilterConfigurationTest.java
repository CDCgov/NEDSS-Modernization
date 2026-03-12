package gov.cdc.nbs.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(
    classes = {
      DSLGlobalGatewayFilterConfigurationTest.TokenRelayMock.class,
      DSLGlobalGatewayFilterConfiguration.class
    })
@ActiveProfiles("oidc")
class DSLGlobalGatewayFilterConfigurationTest {

  private static final GatewayFilter FILTER = mock(GatewayFilter.class);

  @Configuration
  static class TokenRelayMock {

    @Bean
    TokenRelayGatewayFilterFactory mockTokenRelayFilter() {
      TokenRelayGatewayFilterFactory mocked = mock(TokenRelayGatewayFilterFactory.class);
      when(mocked.apply()).thenReturn(FILTER);
      return mocked;
    }
  }

  @Autowired
  @Qualifier("defaults") List<GatewayFilter> defaults;

  @Autowired TokenRelayGatewayFilterFactory factory;

  @Test
  void should_include_TokenRelayFilter_when_oidc_profile_is_active() {

    verify(factory).apply();

    assertThat(defaults).contains(FILTER);
  }
}
