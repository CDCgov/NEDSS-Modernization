package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReportSpecGeneratorTest {
  ReportSpecGenerator generator = new ReportSpecGenerator();

  @Test
  void should_generate_hardcoded_report_spec() {
    ReportSpec reportSpec = generator.generate();
    assertThat(reportSpec.version()).isEqualTo(1);
    assertThat(reportSpec.isBuiltin()).isEqualTo(true);
    assertThat(reportSpec.isExport()).isEqualTo(true);
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_custom");
    assertThat(reportSpec.dataSourceName()).isEqualTo("nbs_rdb.investigation");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }
}
