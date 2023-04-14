package gov.cdc.nbs.patient.profile.race;

import gov.cdc.nbs.accumulation.CollectionMerge;

import java.util.Collection;

class PatientRaceMerger {

    PatientRace merge(final PatientRace current, final PatientRace next) {

        Collection<PatientRace.Race> detailed = CollectionMerge.merged(current.detailed(), next.detailed());

        return new PatientRace(
            current.patient(),
            current.id(),
            current.version(),
            current.asOf(),
            current.category(),
            detailed
        );
    }
}
