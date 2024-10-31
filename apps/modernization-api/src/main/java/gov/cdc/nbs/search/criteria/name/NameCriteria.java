package gov.cdc.nbs.search.criteria.name;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NameCriteria(String name, String operator) {
}
