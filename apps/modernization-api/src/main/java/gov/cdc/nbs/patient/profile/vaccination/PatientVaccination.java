package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.investigation.association.AssociatedWith;

import java.time.Instant;

record PatientVaccination(
    long vaccination,
    Instant createdOn,
    String provider,
    Instant administeredOn,
    String administered,
    String event,
    AssociatedWith associatedWith
) {


}
