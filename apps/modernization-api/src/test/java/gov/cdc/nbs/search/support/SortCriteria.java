package gov.cdc.nbs.search.support;

import org.springframework.data.domain.Sort;

public record SortCriteria(Sort.Direction direction, String field) {}
