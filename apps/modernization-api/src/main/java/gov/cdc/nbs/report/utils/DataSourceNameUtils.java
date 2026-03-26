package gov.cdc.nbs.report.utils;

import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class DataSourceNameUtils {
  private DataSourceNames dataSourceNames = new DataSourceNames();

  public DataSourceNameUtils(DataSourceNames dataSourceNames) {
    this.dataSourceNames = dataSourceNames;
  }

  public String buildDataSourceName(String orgDataSourceName) {
    Map<String, String> mappings = dataSourceNames.getMappings();
    String modifiedName = orgDataSourceName.trim().replace("[", "").replace("]", "");

    int orgDBNameIndex = modifiedName.indexOf(".");
    if (orgDBNameIndex <= 0) {
      throw new IllegalArgumentException(
          String.format("No data source found for %s", orgDataSourceName));
    }

    String orgDBName = modifiedName.substring(0, orgDBNameIndex);

    if (!mappings.containsKey(orgDBName)) {
      if (mappings.values().stream().noneMatch(s -> s.equals(orgDBName))) {
        throw new IllegalArgumentException(
            String.format("No data source found for %s", orgDataSourceName));
      }
    }

    String standardizedDBName = mappings.getOrDefault(orgDBName, orgDBName);
    String dboStr = modifiedName.toLowerCase().contains(".dbo") ? "" : ".dbo";
    String standardizedDataSourceName = String.format("%s%s", standardizedDBName, dboStr);
    String regexStr = "\\b" + Pattern.quote(orgDBName) + "\\b";
    return modifiedName.replaceAll(regexStr, standardizedDataSourceName);
  }
}
