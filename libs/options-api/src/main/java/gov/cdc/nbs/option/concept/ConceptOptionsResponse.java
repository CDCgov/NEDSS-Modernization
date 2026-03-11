package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

public record ConceptOptionsResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String valueSet,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Collection<Option> options) {}
