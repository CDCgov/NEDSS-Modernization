package gov.cdc.nbs.questionbank.valueset.response;

public record ValueSetSearchResponse(
    String type,
    String valueSetCode,
    String valueSetName,
    String valueSetDescription,
    String valueSetStatus
) {
}
