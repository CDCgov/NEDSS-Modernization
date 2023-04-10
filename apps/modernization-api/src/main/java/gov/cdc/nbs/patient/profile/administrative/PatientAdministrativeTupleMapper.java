package gov.cdc.nbs.patient.profile.administrative;

import com.querydsl.core.Tuple;

import java.time.Instant;
import java.util.Objects;

class PatientAdministrativeTupleMapper {

    private final PatientAdministrativeTables tables;

    PatientAdministrativeTupleMapper(final PatientAdministrativeTables tables) {
        this.tables = tables;
    }

    PatientAdministrative map(final Tuple tuple) {

        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "An administrative patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "An administrative identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "An administrative version is required"
            );

        Instant asOf = tuple.get(this.tables.patient().asOfDateAdmin);

        String comment = tuple.get(this.tables.patient().description);

        return new PatientAdministrative(
            patient,
            identifier,
            version,
            asOf,
            comment
        );
    }
}
