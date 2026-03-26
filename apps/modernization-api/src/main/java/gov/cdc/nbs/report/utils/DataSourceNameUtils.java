package gov.cdc.nbs.report.utils;

import java.util.Map;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nbs.report.datasource")
public class DataSourceNameUtils {
  @Getter @Setter Map<String, String> mappings;

  public String buildDataSourceName(String orgDataSourceName) {
    Map<String, String> dataSourceNameMappings = getMappings();
    String modifiedName = orgDataSourceName.trim().replace("[", "").replace("]", "");

    int orgDBNameIndex = modifiedName.indexOf(".");
    if (orgDBNameIndex <= 0) {
      throw new IllegalArgumentException(
          String.format("No data source found for %s", orgDataSourceName));
    }

    String orgDBName = modifiedName.substring(0, orgDBNameIndex);

    if (!dataSourceNameMappings.containsKey(orgDBName)
        && dataSourceNameMappings.values().stream().noneMatch(s -> s.equals(orgDBName))) {
      throw new IllegalArgumentException(
          String.format("No data source found for %s", orgDataSourceName));
    }

    String standardizedDBName = dataSourceNameMappings.getOrDefault(orgDBName, orgDBName);
    String dboStr = modifiedName.toLowerCase().contains(".dbo") ? "" : ".dbo";
    String standardizedDataSourceName = String.format("%s%s", standardizedDBName, dboStr);
    String regexStr = "\\b" + Pattern.quote(orgDBName) + "\\b";
    return modifiedName.replaceAll(regexStr, standardizedDataSourceName);
  }
}
