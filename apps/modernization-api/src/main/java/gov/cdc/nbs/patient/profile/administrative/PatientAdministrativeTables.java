package gov.cdc.nbs.patient.profile.administrative;

import gov.cdc.nbs.entity.odse.QPerson;

record PatientAdministrativeTables(QPerson patient) {

    PatientAdministrativeTables() {
        this(QPerson.person);
    }
}
