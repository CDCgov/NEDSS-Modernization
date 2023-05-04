package gov.cdc.nbs.patient.profile.race;

import gov.cdc.nbs.accumulation.Accumulator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;

class PatientRaceAccumulator {

    static Collector<PatientRace, ?, List<PatientRace>> accumulate(final PatientRaceMerger merger) {
        return Accumulator.collecting(
            PatientRaceAccumulator::unique,
            merger::merge
        );
    }

    private static int unique(final PatientRace race) {
        return Objects.hash(race.id(), race.category().id());
    }

    private PatientRaceAccumulator() {
    }
}
