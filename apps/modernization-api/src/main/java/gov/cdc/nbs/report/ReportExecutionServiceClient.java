package gov.cdc.nbs.report;

import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDateTime;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class ReportExecutionServiceClient {
  private static final System.Logger LOGGER =
      System.getLogger(ReportExecutionServiceClient.class.getName());

  private final Clock clock;

  private final RestClient restClient;
  private final DataSourceNameUtils dataSourceNameUtils;
  private final WhereClauseService whereClauseService;
  private final ReportFetcher reportFetcher;

  public ReportExecutionServiceClient(
      final Clock clock,
      final DataSourceNameUtils dataSourceNameUtils,
      RestClient restClient,
      WhereClauseService whereClauseService,
      ReportFetcher reportFetcher) {
    this.clock = clock;
    this.restClient = restClient;
    this.dataSourceNameUtils = dataSourceNameUtils;
    this.whereClauseService = whereClauseService;
    this.reportFetcher = reportFetcher;
  }

  public void executeReport(ReportExecutionRequest request, HttpServletResponse responseToSet) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = reportFetcher.getReport(reportUid, dataSourceUid);

    ReportSpec reportSpec = buildReportSpec(request, reportConfigResponse);

    LOGGER.log(
        System.Logger.Level.DEBUG, "POSTing report execution request for report " + reportUid);

    restClient
        .post()
        .uri("/report/execute")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.TEXT_PLAIN, MediaType.parseMediaType("text/csv"))
        .body(reportSpec)
        .exchange(
            (req, resp) -> {
              if (resp.getStatusCode().isError()) {
                throw new RestClientResponseException(
                    "Error response from the report-execution service",
                    resp.getStatusCode(),
                    resp.getStatusText(),
                    resp.getHeaders(),
                    resp.getBody().readAllBytes(),
                    null);
              }

              HttpHeaders headers = resp.getHeaders();

              String contextHeader = headers.getFirst("X-Report-Context-Header");
              String description = headers.getFirst("X-Report-Description");

              if (contextHeader != null) {
                responseToSet.setHeader("X-Report-Context-Header", contextHeader);
              }

              if  (description != null) {
                responseToSet.setHeader("X-Report-Description", description);
              }

              responseToSet.setHeader(
                  "X-Report-Timestamp", LocalDateTime.now(this.clock).toString());
              responseToSet.setHeader("X-Report-Query", reportSpec.subsetQuery());

              responseToSet.setContentType("text/csv");
              responseToSet.setCharacterEncoding("UTF-8");

              try (InputStream inputStream = resp.getBody()) {
                inputStream.transferTo(responseToSet.getOutputStream());
              }

              return null;
            });

    LOGGER.log(
        System.Logger.Level.DEBUG,
        "Report execution POST request succeeded for report " + reportUid);
  }

  private ReportSpec buildReportSpec(
      ReportExecutionRequest request, ReportConfiguration reportConfigResponse) {
    if (!reportConfigResponse.isPython()) {
      throw new NotImplementedException(
          "Report not implemented for python", String.valueOf(HttpStatus.NOT_IMPLEMENTED));
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
