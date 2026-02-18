package gov.cdc.nbs.configuration.settings.analytics;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Analytics(String key, String host) {}
