package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.enums.RecordStatus;

public record PatientProfile(long id, String local, short version, String status) {

    public PatientProfile(long id, String local, short version) {
        this(id, local, version, RecordStatus.ACTIVE.display());
    }

}
