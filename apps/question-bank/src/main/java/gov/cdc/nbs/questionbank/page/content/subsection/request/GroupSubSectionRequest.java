package gov.cdc.nbs.questionbank.page.content.subsection.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record GroupSubSectionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String blockName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<Batch> batches,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) int repeatingNbr) {
  public record Batch(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean appearsInTable,
      String label,
      int width) {}
}
