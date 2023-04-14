package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.accumulation.CollectionMerge;

import java.util.Collection;

class PatientEthnicityMerger {

    PatientEthnicity merge(final PatientEthnicity current, final PatientEthnicity next) {

        Collection<PatientEthnicity.Ethnicity> detailed = CollectionMerge.merged(current.detailed(), next.detailed());

        return new PatientEthnicity(
            current.patient(),
            current.id(),
            current.version(),
            current.asOf(),
            current.ethnicGroup(),
            current.unknownReason(),
            detailed
        );

    }
}
