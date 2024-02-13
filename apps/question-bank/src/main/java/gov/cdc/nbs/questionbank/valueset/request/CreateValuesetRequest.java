package gov.cdc.nbs.questionbank.valueset.request;

public record CreateValuesetRequest(
    String type,
    String code,
    String name,
    String description) {

}
