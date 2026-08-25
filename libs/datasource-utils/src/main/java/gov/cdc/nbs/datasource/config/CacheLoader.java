package gov.cdc.nbs.datasource.config;

import gov.cdc.nbs.datasource.utils.ConfigurationValueFinder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CacheLoader {

  private final ConfigurationValueFinder finder;

  public CacheLoader(final ConfigurationValueFinder finder) {
    this.finder = finder;
  }

  /**
   * Fires automatically the exact second the Spring Boot context becomes fully active and ready to
   * handle incoming web or API requests.
   */
  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationStartup() {
    this.finder.loadConfigurations();
  }
}
