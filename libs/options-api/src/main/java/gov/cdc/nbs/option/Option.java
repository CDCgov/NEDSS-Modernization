package gov.cdc.nbs.option;

import io.swagger.v3.oas.annotations.media.Schema;

public record Option(
    @Schema(required = true)
    String value,
    @Schema(required = true)
    String name,
    @Schema(required = true)
    String label,
    int order
) {

}
