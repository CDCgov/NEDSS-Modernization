package gov.cdc.nbs.report.utils;

import gov.cdc.nbs.report.DataSourceNameConfiguration;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DataSourceNameUtils {
  private static final String ERROR_MSG = "No data source found for %s";
  private final DataSourceNameConfiguration config;

  public DataSourceNameUtils(DataSourceNameConfiguration config) {
    this.config = config;
  }

  /**
   * Given a data source name (e.g. nbs.dbo.PHCDemographic), return the standardized data source
   * name
   *
   * @param orgDataSourceName original data source name (e.g. nbs.dbo.PHCDemographic)
   * @return standardized data source name (e.g. [NBS_ODSE].[dbo].[PHCDemographic])
   */
  public String buildDataSourceName(String orgDataSourceName) {
    String standardizedDBName = buildDBName(orgDataSourceName);
    String dboStr = "[dbo]";
    String standardizedTableName = buildTableName(orgDataSourceName);
    return String.format("%s.%s.%s", standardizedDBName, dboStr, standardizedTableName);
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
    Map<String, String> dataSourceNameMappings = config.getMappings();
    String modifiedDataSourceName = removeBrackets(orgDataSourceName);

    int dataSourceParts = modifiedDataSourceName.split("\\.").length;
    int orgDBNameIndex = modifiedDataSourceName.indexOf(".");
    boolean isInvalidDataSourceName = dataSourceParts > 3 || orgDBNameIndex <= 0;
    if (isInvalidDataSourceName) {
      throw new IllegalArgumentException(String.format(ERROR_MSG, orgDataSourceName));
    }

    String orgDBName = modifiedDataSourceName.substring(0, orgDBNameIndex);
    String standardizedDBName = "";

    // Check if it's an alias (key)
    if (dataSourceNameMappings.containsKey(orgDBName)) {
      standardizedDBName = dataSourceNameMappings.get(orgDBName);
    }

    // Check if it's already a valid standardized name (value)
    if (dataSourceNameMappings.containsValue(orgDBName)) {
      standardizedDBName = orgDBName;
    }

    // check if DBName has an alias or is already a valid DB name
    if (standardizedDBName.isEmpty()) {
      throw new IllegalArgumentException(String.format(ERROR_MSG, orgDataSourceName));
    }

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
