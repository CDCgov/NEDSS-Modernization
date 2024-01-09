package gov.cdc.nbs.questionbank.valueset.request;

public record ValueSetCreateRequest(
    String valueSetType,
    String valueSetCode,
    String valueSetName,
    String valueSetDescription
) {

}
