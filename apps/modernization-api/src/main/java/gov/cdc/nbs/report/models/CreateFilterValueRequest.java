package gov.cdc.nbs.report.models;

public record CreateFilterValueRequest(
    Integer sequenceNumber, String valueType, String operator, String valueTxt) {}
