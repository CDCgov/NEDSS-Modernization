package gov.cdc.nbs.datasource.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataSourceNameUtilsTest {

  private static final Map<String, String> DATABASE_MAPPINGS =
      Map.of(
          "nbs_ods", "NBS_ODSE",
          "nbs_odse", "NBS_ODSE",
          "odse", "NBS_ODSE",
          "ods", "NBS_ODSE");

  @Mock ConfigurationValueFinder config;
  @InjectMocks private DataSourceNameUtils utils;

  @BeforeEach
  void setUp() {
    lenient()
        .when(config.getConfigValue(anyString()))
        .thenAnswer(
            invocation -> {
              String key = invocation.getArgument(0, String.class);
              return DATABASE_MAPPINGS.getOrDefault(key.toLowerCase(), "");
            });
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "nonExistentName",
        "",
        "nbs_fake_db.dbo.demographics",
        "nbs_rdb.AGGREGATE_REPORT_DATAMART",
        "nbs_ods.",
        ".",
        "nbs_ods.dbo.table.test"
      })
  void buildDataSourceName_should_throw_illegal_argument_when_no_match_found(
      String dataSourceName) {
    assertThatThrownBy(() -> utils.buildDataSourceName(dataSourceName))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format("No data source found for %s", dataSourceName));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "NBS_ODSE.dbo.PHCDemographic",
        "nbs_ods.PHCDemographic",
        "odse.[PHCDemographic]",
        "ods.[dbo].[PHCDemographic]",
        "[ods].[dbo].PHCDemographic"
      })
  void buildDataSourceName_should_return_standardized_nbs_odse_name(String dataSourceName) {
    String result = utils.buildDataSourceName(dataSourceName);
    assertThat(result).isEqualTo("[NBS_ODSE].[dbo].[PHCDemographic]");
  }

  @Test
  void buildDataSourceName_should_return_standardized_data_source_name() {
    String result = utils.buildDataSourceName("ods.dbo_phcdemographic");
    assertThat(result).isEqualTo("[NBS_ODSE].[dbo].[dbo_phcdemographic]");
  }
}
