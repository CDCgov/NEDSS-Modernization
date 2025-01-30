package gov.cdc.nbs.configuration.settings.smarty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class SmartyConfiguration {

  @Bean
  @Scope("prototype")
  Smarty smarty(
      @Value("${nbs.ui.settings.smarty.key:#{null}}")
      final String key
  ) {
    return new Smarty(key);
  }

}
