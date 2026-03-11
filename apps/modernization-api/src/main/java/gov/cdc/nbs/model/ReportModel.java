package gov.cdc.nbs.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReportModel(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportIdResponse id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner) {

  private static class ReportIdResponse {
    Long reportId;
    Long dataSourceId;

    public ReportIdResponse(Long reportId, Long dataSourceId) {
      this.reportId = reportId;
      this.dataSourceId = dataSourceId;
    }
  }

  public ReportModel(Long reportId, Long dataSourceId, String runner) {
    this(new ReportIdResponse(reportId, dataSourceId), runner);
  }
}
