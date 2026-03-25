package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gov.cdc.nbs.report.utils.DataSourceNameUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DataSourceNameUtilsTest {
  @InjectMocks private DataSourceNameUtils utils;

  @BeforeEach
  void setUp() {
    utils = new DataSourceNameUtils();
    ReflectionTestUtils.setField(utils, "nbsOdseDbName", "NBS_ODSE");
    ReflectionTestUtils.setField(utils, "nbsOdseAltNames", List.of("nbs_ods", "odse", "ods"));
    ReflectionTestUtils.setField(utils, "rdbDbName", "RDB");
    ReflectionTestUtils.setField(utils, "rdbAltNames", List.of(""));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "nonExistentName",
        "",
        "nbs_fake_db.dbo.demographics",
        "nbs_rdb.AGGREGATE_REPORT_DATAMART"
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
        "[nbS_odS].[PHCDemographic]",
        "ods.[dbo].[PHCDemographic]",
        "ODS.PHCDemographic"
      })
  void buildDataSourceName_should_return_standardized_nbs_odse_name(String dataSourceName) {
    String result = utils.buildDataSourceName(dataSourceName);
    assertThat(result).isEqualTo("NBS_ODSE.dbo.PHCDemographic");
  }
}
