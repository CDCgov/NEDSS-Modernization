package gov.cdc.nbs.event.search;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LabReportFilterTest {

  @Test
  void should_not_return_accession_without_identifier() {

    LabReportFilter filter = new LabReportFilter();

    filter.setEventId(
        new LabReportFilter.LabReportEventId(
            LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER,
            null
        )
    );

    assertThat(filter.accession()).isEmpty();
  }

  @Test
  void should_not_return_lab_if_without_identifier() {

    LabReportFilter filter = new LabReportFilter();

    filter.setEventId(
        new LabReportFilter.LabReportEventId(
            LabReportFilter.LaboratoryEventIdType.LAB_ID,
            null
        )
    );

    assertThat(filter.labId()).isEmpty();
  }

  @Test
  void should_not_return_resulted_test_when_empty() {
    LabReportFilter filter = new LabReportFilter();

    filter.setResultedTest("");

    assertThat(filter.withResultedTest()).isEmpty();
  }

  @Test
  void should_not_return_coded_result_when_empty() {
    LabReportFilter filter = new LabReportFilter();

    filter.setCodedResult("");

    assertThat(filter.withCodedResult()).isEmpty();
  }
}
