package gov.cdc.nbs.patient.profile;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;

import java.time.Instant;
import java.util.Objects;

class PatientProfileTupleMapper {

    private final QPerson patient;

    PatientProfileTupleMapper(final QPerson patient) {
        this.patient = patient;
    }

    PatientProfile map(final Instant asOf, final Tuple tuple) {
        long identifier = Objects.requireNonNull(
            tuple.get(this.patient.id),
            "A patient id is required"
        );

        String local = tuple.get(this.patient.localId);
        short version =
            Objects.requireNonNull(
                tuple.get(this.patient.versionCtrlNbr),
                "A patient version is required"
            );

        return new PatientProfile(
            identifier,
            local,
            asOf,
            version
        );
    }
}
