package gov.cdc.nbs.report.utils;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "nbs.report.datasource")
public class DataSourceNames {

  Map<String, String> mappings;
}
