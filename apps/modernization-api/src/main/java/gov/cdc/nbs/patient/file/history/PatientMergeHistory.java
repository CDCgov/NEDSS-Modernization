package gov.cdc.nbs.patient.file.history;

public record PatientMergeHistory(
    String supersededPersonLocalId,
    String supersededPersonLegalName,
    String mergeTimestamp,
    String mergedByUser) {}
