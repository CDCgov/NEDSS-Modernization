package gov.cdc.nbs.configuration.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nbs.ui.settings")
public record Settings(Smarty smarty, Analytics analytics) {

  public record Smarty(String key) {}
  public record Analytics(String key, String host) {}
}
