package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReportConfigurationTest {
  @Test
  void should_create_report_configuration_with_idMap() {
    Long reportUid = 1L;
    Long dataSourceUid = 2L;
    String runner = "python";

    ReportConfiguration reportConfig = new ReportConfiguration(reportUid, dataSourceUid, runner);

    assertThat(reportConfig.runner()).isEqualTo("python");
    assertThat(reportConfig.id()).containsEntry("reportUid", 1L);
    assertThat(reportConfig.id()).containsEntry("dataSourceUid", 2L);
    assertThat(reportConfig.id()).hasSize(2);
  }
}
