package gov.cdc.nbs.questionbank.filter;

import java.time.LocalDate;

public record DateRangeFilter(String property, LocalDate after, LocalDate before)
    implements Filter {}
