package gov.cdc.nbs.report;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nbs.report.datasource")
public class DataSourceNameConfiguration {
  @Getter @Setter Map<String, String> mappings;
}
