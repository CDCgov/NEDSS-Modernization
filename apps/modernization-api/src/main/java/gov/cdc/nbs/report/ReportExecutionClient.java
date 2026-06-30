package gov.cdc.nbs.report;

import gov.cdc.nbs.datasource.utils.DataSourceNameConfiguration;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.models.LibraryExecutionResult;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportExecutionResult;
import gov.cdc.nbs.report.models.ReportSpec;
import java.time.Clock;
import java.time.LocalDateTime;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class ReportExecutionClient {
  private final Clock clock;

  private final RestClient restClient;
  private final DataSourceNameUtils dataSourceNameUtils;
  private final WhereClauseService whereClauseService;
  private final ReportFetcher reportFetcher;

  public ReportExecutionClient(
      final Clock clock,
      final DataSourceNameConfiguration dataSourceNameConfig,
      RestClient restClient,
      WhereClauseService whereClauseService,
      ReportFetcher reportFetcher) {
    this.clock = clock;
    this.restClient = restClient;
    this.dataSourceNameUtils = new DataSourceNameUtils(dataSourceNameConfig);
    this.whereClauseService = whereClauseService;
    this.reportFetcher = reportFetcher;
  }

  public ReportExecutionResult executeReport(ReportExecutionRequest request) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = reportFetcher.getReport(reportUid, dataSourceUid);

    ReportSpec reportSpec = buildReportSpec(request, reportConfigResponse);

    LibraryExecutionResult result =
        restClient
            .post()
            .uri("/report/execute")
            .contentType(MediaType.APPLICATION_JSON)
            .body(reportSpec)
            .retrieve()
            .onStatus(
                status -> status.value() >= 400,
                (req, resp) -> {
                  throw new RestClientResponseException(
                      "Error response from the report-execution service",
                      resp.getStatusCode(),
                      resp.getStatusText(),
                      resp.getHeaders(),
                      resp.getBody().readAllBytes(),
                      null);
                })
            .toEntity(LibraryExecutionResult.class)
            .getBody();

    if (result == null) {
      throw new IllegalStateException(
          "No error response and no body parsed from report execution service");
    }

    return new ReportExecutionResult(
        result, reportSpec.subsetQuery(), LocalDateTime.now(this.clock));
  }

  private ReportSpec buildReportSpec(
      ReportExecutionRequest request, ReportConfiguration reportConfigResponse) {
    if (!reportConfigResponse.isPython()) {
      throw new NotImplementedException(
          String.format("Report not implemented for %s", reportConfigResponse.library().runner()),
          String.valueOf(HttpStatus.NOT_IMPLEMENTED));
    }

    if (request.columnUids() != null && request.columnUids().isEmpty()) {
      throw new UnprocessableEntityException(
          "Column UIDs cannot be empty - if omitting reportColumns, use `null`");
    }

    ReportSpecBuilder specBuilder =
        new ReportSpecBuilder(
            request, reportConfigResponse, dataSourceNameUtils, whereClauseService);
    return specBuilder.build();
  }
}
