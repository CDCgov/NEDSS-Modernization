package gov.cdc.nbs.report.utils;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nbs.report.datasource")
public class DataSourceNameUtils {
  @Getter @Setter Map<String, String> mappings;

  private static final String ERROR_MSG = "No data source found for %s";

  /**
   * Given a data source name (e.g. nbs.dbo.PHCDemographic), return the standardized data source
   * name
   *
   * @param orgDataSourceName original data source name (e.g. nbs.dbo.PHCDemographic)
   * @return standardized data source name (e.g. [NBS_ODSE].[dbo].[PHCDemographic])
   */
  public String buildDataSourceName(String orgDataSourceName) {
    String standardizedDBName = buildDBName(orgDataSourceName);
    String dboStr = ".[dbo].";
    String standardizedTableName = buildTableName(orgDataSourceName);
    return String.format("%s%s%s", standardizedDBName, dboStr, standardizedTableName);
  }

  /**
   * Given a data source name (e.g. nbs.dbo.PHCDemographic), return the standardized DB name, which
   * is defined in the application.yml, in brackets (e.g. [NBS_ODSE])
   *
   * @param orgDataSourceName original data source name (e.g. nbs.dbo.PHCDemographic)
   * @return standardized DB name in brackets (e.g. [NBS_ODSE])
   * @throws IllegalArgumentException if the DB does not exist either in the original data source
   *     name, or an alias does not exist in the application.yml, or it is not a valid DB name
   */
  private String buildDBName(String orgDataSourceName) {
    Map<String, String> dataSourceNameMappings = getMappings();
    String modifiedDataSourceName = removeBrackets(orgDataSourceName);

    int orgDBNameIndex = modifiedDataSourceName.indexOf(".");
    if (orgDBNameIndex <= 0) {
      throw new IllegalArgumentException(String.format(ERROR_MSG, orgDataSourceName));
    }

    String orgDBName = modifiedDataSourceName.substring(0, orgDBNameIndex);

    // check if DBName has an alias or is already a valid DB name
    if (!dataSourceNameMappings.containsKey(orgDBName)
        && dataSourceNameMappings.values().stream().noneMatch(s -> s.equals(orgDBName))) {
      throw new IllegalArgumentException(String.format(ERROR_MSG, orgDataSourceName));
    }

    String standardizedDBName = dataSourceNameMappings.getOrDefault(orgDBName, orgDBName);
    return String.format("[%s]", standardizedDBName);
  }

  /**
   * Given a data source name (e.g. nbs.dbo.PHCDemographic), return table name in brackets (e.g.
   * [PHCDemographic])
   *
   * @param orgDataSourceName original data source name (e.g. nbs.dbo.PHCDemographic)
   * @return table name in brackets (e.g. [PHCDemographic])
   * @throws IllegalArgumentException if a table name does not exist in the provided
   *     orgDataSourceName
   */
  private String buildTableName(String orgDataSourceName) {
    String modifiedDataSourceName = removeBrackets(orgDataSourceName);
    int orgTableNameIndex = modifiedDataSourceName.lastIndexOf(".");
    boolean isLastChar = orgTableNameIndex == orgDataSourceName.length() - 1;
    if (orgTableNameIndex <= 0 || isLastChar) {
      throw new IllegalArgumentException(String.format(ERROR_MSG, orgDataSourceName));
    }

    return String.format("[%s]", modifiedDataSourceName.substring(orgTableNameIndex + 1));
  }

  private String removeBrackets(String orgDataSourceName) {
    return orgDataSourceName.trim().replace("[", "").replace("]", "");
  }
}
