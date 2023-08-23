package gov.cdc.nbs.patient.profile.vaccination;

import java.time.Instant;
import gov.cdc.nbs.event.investigation.association.AssociatedWith;

record PatientVaccination(
        long vaccination,
        Instant createdOn,
        String provider,
        Instant administeredOn,
        String administered,
        String event,
        AssociatedWith associatedWith) {


}
