package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestBodySpec;
import org.springframework.web.client.RestClient.RequestBodyUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

  @Mock private ReportRepository reportRepository;
  @Mock private RestClient reportExecutionClient;
  @Mock private ReportSpecBuilder specBuilder;
  @Mock private ReportLibrary reportLibrary;
  @Mock private List<ReportFilter> dbReportFilters;

  @Mock private RequestBodyUriSpec requestBodyUriSpec;
  @Mock private RequestBodySpec requestBodySpec;
  @Mock private ResponseSpec responseSpec;

  @InjectMocks private ReportService service;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;

  private void mockReport(ReportId id, String runner) {
    Report report = mock(Report.class);

    when(report.getReportLibrary()).thenReturn(reportLibrary);
    when(report.getReportFilters()).thenReturn(dbReportFilters);
    when(reportLibrary.getRunner()).thenReturn(runner);
    when(reportRepository.findById(id)).thenReturn(Optional.of(report));
  }

  private void assertColumnsMatch(
      DataSourceColumn dbColumn, gov.cdc.nbs.report.models.DataSourceColumn column) {
    assertThat(dbColumn.getId()).isEqualTo(column.id());
    assertThat(dbColumn.getColumnMaxLength()).isEqualTo(column.columnMaxLength());
    assertThat((dbColumn.getColumnName())).isEqualTo(column.columnName());
    assertThat((dbColumn.getColumnTitle())).isEqualTo(column.columnTitle());
    assertThat((dbColumn.getColumnSourceTypeCode())).isEqualTo(column.columnSourceTypeCode());
    assertThat((dbColumn.getDescTxt())).isEqualTo(column.descTxt());
    assertThat((dbColumn.getDisplayable())).isEqualTo(column.displayable());
    assertThat((dbColumn.getFilterable())).isEqualTo(column.filterable());
    assertThat((dbColumn.getStatusCd())).isEqualTo(column.statusCd());
    assertThat((dbColumn.getStatusTime())).isEqualTo(column.statusTime());
  }

  private void assertFilterCodesMatch(
      FilterCode dbFilterCode, gov.cdc.nbs.report.models.FilterCode filterCode) {
    assertThat(dbFilterCode.getId()).isEqualTo(filterCode.id());
    assertThat(dbFilterCode.getCodeTable()).isEqualTo(filterCode.codeTable());
    assertThat(dbFilterCode.getDescTxt()).isEqualTo(filterCode.descTxt());
    assertThat(dbFilterCode.getCode()).isEqualTo(filterCode.code());
    assertThat(dbFilterCode.getFilterCodeSetName()).isEqualTo(filterCode.filterCodeSetName());
    assertThat(dbFilterCode.getFilterType()).isEqualTo(filterCode.filterType());
    assertThat(dbFilterCode.getFilterName()).isEqualTo(filterCode.filterName());
  }

  private void assertFilterValuesMatch(
      FilterValue dbFilterValue, gov.cdc.nbs.report.models.FilterValue filterValue) {
    assertThat(dbFilterValue.getId()).isEqualTo(filterValue.id());
    assertThat(dbFilterValue.getSequenceNumber()).isEqualTo(filterValue.sequenceNumber());
    assertThat(dbFilterValue.getValueType()).isEqualTo(filterValue.valueType());
    assertThat(dbFilterValue.getColumnUid()).isEqualTo(filterValue.columnUid());
    assertThat(dbFilterValue.getOperator()).isEqualTo(filterValue.operator());
    assertThat(dbFilterValue.getValueTxt()).isEqualTo(filterValue.valueTxt());
  }

  @Test
  void getReport_should_return_configuration_when_report_exists() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "python");

    ReportConfiguration config = service.getReport(reportUid, dataSourceUid);

    assertThat(config.runner()).isEqualTo("python");
    assertThat(config.filters())
        .allSatisfy(
            filter -> {
              Optional<ReportFilter> matchingReportFilter =
                  dbReportFilters.stream()
                      .filter(f -> f.getId().equals(filter.reportFilterUid()))
                      .findAny();

              assertThat(matchingReportFilter).isPresent();

              DataSourceColumn dbColumn = matchingReportFilter.get().getDataSourceColumn();
              gov.cdc.nbs.report.models.DataSourceColumn column = filter.dataSourceColumn();
              assertColumnsMatch(dbColumn, column);

              FilterCode dbFilterCode = matchingReportFilter.get().getFilterCode();
              gov.cdc.nbs.report.models.FilterCode filterCode = filter.filterCode();
              assertFilterCodesMatch(dbFilterCode, filterCode);

              assertThat(matchingReportFilter.get().getFilterValues())
                  .allSatisfy(
                      dbFilterValue -> {
                        Optional<gov.cdc.nbs.report.models.FilterValue> optionalFilterValue =
                            filter.filterValues().stream()
                                .filter(f -> f.id().equals(dbFilterValue.getId()))
                                .findAny();

                        assertThat(optionalFilterValue).isPresent();
                        gov.cdc.nbs.report.models.FilterValue matchingFilterValue =
                            optionalFilterValue.get();

                        assertFilterValuesMatch(dbFilterValue, matchingFilterValue);
                      });
            });
  }

  @Test
  void getReport_should_throw_when_report_not_found() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    when(reportRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getReport(reportUid, dataSourceUid))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
  }

  @Test
  void executeReport_should_return_response_when_report_exists_and_runner_is_python() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "python");

    ReportSpec spec =
        new ReportSpec(
            1,
            true,
            true,
            "Test Report",
            "nbs_custom",
            "nbs_rdb.investigation",
            "SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]",
            null);
    when(specBuilder.build()).thenReturn(spec);

    when(reportExecutionClient.post()).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.uri("/report/execute")).thenReturn(requestBodySpec);
    when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
    when(requestBodySpec.body(any(ReportSpec.class))).thenReturn(requestBodySpec);
    when(requestBodySpec.retrieve()).thenReturn(responseSpec);

    ResponseEntity<String> expectedResponse = new ResponseEntity<>("result", HttpStatus.OK);
    when(responseSpec.toEntity(String.class)).thenReturn(expectedResponse);

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(16L), List.of());

    ResponseEntity<String> response = service.executeReport(request);

    assertThat(response).isEqualTo(expectedResponse);
    verify(specBuilder).build();
  }

  @Test
  void executeReport_should_throw_not_implemented_when_runner_not_python() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "java");

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(17L), List.of());

    assertThatThrownBy(() -> service.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessage("Report not implemented for java");
  }

  @Test
  void executeReport_should_throw_not_found_when_report_not_found() {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    when(reportRepository.findById(id)).thenReturn(Optional.empty());

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(18L), List.of());

    assertThatThrownBy(() -> service.executeReport(request))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
  }
}
