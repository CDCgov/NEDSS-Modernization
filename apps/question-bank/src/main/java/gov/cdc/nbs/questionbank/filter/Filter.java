package gov.cdc.nbs.questionbank.filter;
@SuppressWarnings(
    //  Sealed interfaces require the implementing classes be listed if not in the same file
    "javaarchitecture:S7027"
)
public sealed interface Filter permits DateFilter, DateRangeFilter, ValueFilter {

  String property();

}
