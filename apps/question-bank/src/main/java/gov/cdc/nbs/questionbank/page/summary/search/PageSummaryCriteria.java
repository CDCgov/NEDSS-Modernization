package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.filter.Filter;
import java.util.Collection;

public record PageSummaryCriteria(String search, Collection<Filter> filters) {}
