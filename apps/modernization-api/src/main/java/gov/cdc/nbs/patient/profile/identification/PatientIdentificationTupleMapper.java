package gov.cdc.nbs.patient.profile.identification;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QEntityId;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;

import java.time.LocalDate;
import java.util.Objects;

class PatientIdentificationTupleMapper {

    record Tables(
        QPerson patient,
        QEntityId identification,
        QCodeValueGeneral type,
        QCodeValueGeneral authority
    ) {
        Tables() {
            this(
                QPerson.person,
                QEntityId.entityId,
                new QCodeValueGeneral("type"),
                new QCodeValueGeneral("authority")
            );
        }
    }


    private final Tables tables;

    PatientIdentificationTupleMapper(final PatientIdentificationTupleMapper.Tables tables) {
        this.tables = tables;
    }

    PatientIdentification map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(tables.patient().id),
            "An identification patient is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "An identification version is required"
            );

        short sequence = Objects.requireNonNull(
            tuple.get(tables.identification().id.entityIdSeq),
            "An identification sequence is required"
        );

        LocalDate asOf = tuple.get(this.tables.identification().asOfDate);

        PatientIdentification.Type type = mapUse(tuple);

        PatientIdentification.Authority authority = maybeMapAuthority(tuple);

        String value = tuple.get(tables.identification().rootExtensionTxt);

        return new PatientIdentification(
            patient,
            sequence,
            version,
            asOf,
            type,
            authority,
            value
        );
    }

    private PatientIdentification.Type mapUse(final Tuple tuple) {
        String id = tuple.get(this.tables.identification().typeCd);
        String description = tuple.get(this.tables.type().codeShortDescTxt);

        return new PatientIdentification.Type(
            id,
            description
        );
    }

    private PatientIdentification.Authority maybeMapAuthority(final Tuple tuple) {
        String id = tuple.get(this.tables.identification().assigningAuthorityCd);
        String description = tuple.get(this.tables.authority().codeShortDescTxt);

        return id == null
            ? null
            : resolveAuthority(id, description);
    }

    private PatientIdentification.Authority resolveAuthority(final String id, final String description) {
        return new PatientIdentification.Authority(
            id,
            description == null ? id : description
        );
    }
}
