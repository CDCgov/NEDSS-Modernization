package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.option.Option;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;

public record ConceptOptionsResponse(
    @Schema(required = true)
    String valueSet,
    @Schema(required = true)
    Collection<Option> options
) {

}
