package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;

public record AdminReportRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @Positive Long dataSourceId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @Positive Long libraryId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank String reportTitle,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank String sectionCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long ownerId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank String group,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull List<UpsertFilterRequest> filterRequests,
    String description) {
  public AdminReportRequest {
    //  Jakarta does not offer an Enum validation annotation, so here we are
    if (Arrays.stream(ReportConstants.ReportGroup.values())
        .noneMatch(g -> g.toString().equals(group))) {
      throw new IllegalArgumentException("Invalid report group: " + group);
    }
  }
}
