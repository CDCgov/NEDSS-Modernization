package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;

class PatientChangeHash {

  static long compute(final Person patient) {
    return patient.signature();
  }

  private PatientChangeHash() {
    //  NOOP
  }
}
