package gov.cdc.nbs.report.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.report.DataSourceNameConfiguration;
import java.util.HashMap;
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
  @Mock DataSourceNameConfiguration config;
  @InjectMocks private DataSourceNameUtils utils;

  @BeforeEach
  void setUp() {
    Map<String, String> mappings = new HashMap<>();
    mappings.put("nbs_ods", "NBS_ODSE");
    mappings.put("odse", "NBS_ODSE");
    mappings.put("ods", "NBS_ODSE");
    when(config.getMappings()).thenReturn(mappings);
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
