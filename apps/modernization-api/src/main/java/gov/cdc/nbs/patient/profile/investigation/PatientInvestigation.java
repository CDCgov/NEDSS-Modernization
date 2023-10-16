package gov.cdc.nbs.patient.profile.investigation;

import java.time.LocalDate;

record PatientInvestigation(
    long investigation,
    LocalDate startedOn,
    String condition,
    String status,
    String caseStatus,
    String jurisdiction,
    String event,
    String coInfection,
    String notification,
    String investigator,
    boolean comparable
) {
}
