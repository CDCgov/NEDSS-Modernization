package gov.cdc.nbs.patient.profile;

import com.querydsl.core.Tuple;

import gov.cdc.nbs.entity.odse.QPerson;

import java.util.Objects;

class PatientProfileTupleMapper {

    record Tables(
        QPerson patient
    ) {
        Tables() {
            this(QPerson.person);
        }
    }


    private final Tables tables;

    PatientProfileTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    PatientProfile map(final Tuple tuple) {
        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "A patient id is required"
        );

        String local = tuple.get(this.tables.patient().personParentUid.localId);
        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().personParentUid.versionCtrlNbr),
                "A patient version is required"
            );
        String recordStatusCd = tuple.get(this.tables.patient().personParentUid.recordStatusCd).toString();

        return new PatientProfile(
            identifier,
            local,
            version,
            recordStatusCd);
    }
}
