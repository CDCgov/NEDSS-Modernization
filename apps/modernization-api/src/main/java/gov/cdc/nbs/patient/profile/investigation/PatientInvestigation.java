package gov.cdc.nbs.patient.profile.investigation;

import java.time.Instant;

record PatientInvestigation(
    long investigation,
    Instant startedOn,
    String condition,
    String status,
    String caseStatus,
    String jurisdiction,
    String event,
    String coInfection,
    String notification,
    String investigator
) {
}
