package gov.cdc.nbs.questionbank.valueset.response;

public record RaceConcept(
    String localCode,
    String codeSetName,
    String display,
    String longName,
    String codeSystem,
    String status,
    String effectiveFromTime,
    String effectiveToTime) {
}
