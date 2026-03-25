package gov.cdc.nbs.report.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSourceNameUtils {
  @Value("${nbs.report.datasource-mappings.nbs_odse.dbname}")
  private String nbsOdseDbName;

  @Value("${nbs.report.datasource-mappings.nbs_odse.alt}")
  private List<String> nbsOdseAltNames;

  @Value("${nbs.report.datasource-mappings.rdb.dbname}")
  private String rdbDbName;

  @Value("${nbs.report.datasource-mappings.rdb.alt}")
  private List<String> rdbAltNames;

  public String buildDataSourceName(String orgDataSourceName) {
    String modifiedName = orgDataSourceName.trim().replace("[", "").replace("]", "");
    Map<String, String> match = getDataSourceMatch(modifiedName);
    Optional<String> optDbName = match.keySet().stream().findFirst();
    Optional<String> optNameToReplace = match.values().stream().findFirst();

    if (optDbName.isEmpty() || optNameToReplace.isEmpty()) {
      throw new IllegalArgumentException("Missing data source name");
    }

    String dbName = optDbName.get();
    String nameToReplace = optNameToReplace.get();
    String dboStr = modifiedName.toLowerCase().contains(".dbo") ? "" : ".dbo";
    String standardizedDataSourceName = String.format("%s%s", dbName, dboStr);
    String regexStr = "\\b" + Pattern.quote(nameToReplace) + "\\b";
    return modifiedName.replaceAll(regexStr, standardizedDataSourceName);
  }

  private Map<String, String> getDataSourceMatch(String orgDataSourceName) {
    Map<String, List<String>> mappings = getDataSourceMappings();
    int orgDBNameIndex = orgDataSourceName.indexOf(".");
    if (orgDBNameIndex <= 0) {
      throw new IllegalArgumentException(
          String.format("No data source found for %s", orgDataSourceName));
    }
    String orgDBName = orgDataSourceName.substring(0, orgDBNameIndex);

    if (mappings.containsKey(orgDBName.toUpperCase())) {
      return Map.of(orgDBName.toUpperCase(), orgDBName);
    }

    return mappings.entrySet().stream()
        .filter(e -> e.getValue().stream().anyMatch(orgDBName::equalsIgnoreCase))
        .findFirst()
        .map(e -> Map.of(e.getKey(), orgDBName))
        .orElseThrow(
            () -> new IllegalArgumentException("No data source found for " + orgDataSourceName));
  }

  private Map<String, List<String>> getDataSourceMappings() {
    Map<String, List<String>> dataSourceMappings = new HashMap<>();
    dataSourceMappings.put(nbsOdseDbName.toUpperCase(), nbsOdseAltNames);
    dataSourceMappings.put(rdbDbName.toUpperCase(), rdbAltNames);
    return dataSourceMappings;
  }
}
