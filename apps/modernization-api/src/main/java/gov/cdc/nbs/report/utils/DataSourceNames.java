package gov.cdc.nbs.report.utils;

import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "nbs.report.datasource")
public class DataSourceNames {

  Map<String, String> mappings;
}
