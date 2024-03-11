package gov.cdc.nbs.questionbank.page.content.subsection.request;

import java.util.List;
import io.swagger.annotations.ApiModelProperty;

public record GroupSubSectionRequest(
    @ApiModelProperty(required = true) String blockName,
    @ApiModelProperty(required = true) List<Batch> batches,
    @ApiModelProperty(required = true) int repeatingNbr) {
  public record Batch(
      @ApiModelProperty(required = true) long id,
      @ApiModelProperty(required = true) boolean appearsInTable,
      String label,
      int width) {
  }

}
