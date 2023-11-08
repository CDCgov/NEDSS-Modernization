package gov.cdc.nbs.questionbank.filter;

public sealed interface Filter permits DateFilter, DateRangeFilter, ValueFilter {

  String property();

}
