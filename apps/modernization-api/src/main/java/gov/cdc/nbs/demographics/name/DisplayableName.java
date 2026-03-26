package gov.cdc.nbs.demographics.name;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DisplayableName(
    String type, String first, String middle, String last, String suffix) {}
