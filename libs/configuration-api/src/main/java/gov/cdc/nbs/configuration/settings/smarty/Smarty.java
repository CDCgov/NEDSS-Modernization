package gov.cdc.nbs.configuration.settings.smarty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Smarty(String key) {
}
