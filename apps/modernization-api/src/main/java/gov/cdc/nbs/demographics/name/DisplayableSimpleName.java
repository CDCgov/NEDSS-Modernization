package gov.cdc.nbs.demographics.name;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DisplayableSimpleName(String prefix, String first, String last) {}
