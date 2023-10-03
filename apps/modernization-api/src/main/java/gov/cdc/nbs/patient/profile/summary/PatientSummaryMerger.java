package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.accumulation.CollectionMerge;

import java.util.Collection;

class PatientSummaryMerger {

    PatientSummary merge(final PatientSummary left, final PatientSummary right) {

        PatientSummary.Address address = coalesce(left.address(), right.address());

        Collection<PatientSummary.Phone> phones = CollectionMerge.merged(left.phone(), right.phone());
        Collection<PatientSummary.Email> emails = CollectionMerge.merged(left.email(), right.email());

        return new PatientSummary(
            left.patient(),
            left.legalName(),
            left.birthday(),
            left.age(),
            left.gender(),
            left.ethnicity(),
            phones,
            emails,
            address
        );
    }

    PatientSummary.Address coalesce(final PatientSummary.Address left, final PatientSummary.Address right) {
        return left == null ? right : left;
    }
}
