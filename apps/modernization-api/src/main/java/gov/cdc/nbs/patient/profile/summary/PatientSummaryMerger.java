package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.accumulation.CollectionMerge;

import java.util.Collection;

class PatientSummaryMerger {

  PatientSummary merge(final PatientSummary left, final PatientSummary right) {

    Collection<PatientSummary.Phone> phones = CollectionMerge.merged(left.phone(), right.phone());
    Collection<PatientSummary.Email> emails = CollectionMerge.merged(left.email(), right.email());

    return new PatientSummary(
        left.asOf(),
        left.patient(),
        left.legalName(),
        left.birthday(),
        left.age(),
        left.gender(),
        left.ethnicity(),
        phones,
        emails
    );
  }

}
