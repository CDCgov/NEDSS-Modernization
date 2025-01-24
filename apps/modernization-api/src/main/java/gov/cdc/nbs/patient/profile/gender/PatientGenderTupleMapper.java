package gov.cdc.nbs.patient.profile.gender;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.message.enums.Gender;

import java.time.LocalDate;
import java.util.Objects;

class PatientGenderTupleMapper {

    record Tables(
        QPerson patient,
        QCodeValueGeneral unknownGenderReason,
        QCodeValueGeneral preferred
    ) {

        Tables() {
            this(
                QPerson.person,
                new QCodeValueGeneral("unknown_reason"),
                new QCodeValueGeneral("preferred_gender")
            );
        }

    }


    private final PatientGenderTupleMapper.Tables tables;


    PatientGenderTupleMapper(final PatientGenderTupleMapper.Tables tables) {
        this.tables = tables;
    }

    PatientGender map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "A gender patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "A gender identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "A gender version is required"
            );

        LocalDate asOf = tuple.get(this.tables.patient().asOfDateSex);

        PatientGender.Gender birth = resolveGender(tuple.get(this.tables.patient().birthGenderCd));

        PatientGender.Gender current = resolveGender(tuple.get(this.tables.patient().currSexCd));

        PatientGender.UnknownReason unknownReason = resolveUnknownReason(tuple);

        PatientGender.PreferredGender preferred = resolvePreferredGender(tuple);

        String additional = tuple.get(this.tables.patient().additionalGenderCd);



        return new PatientGender(
            patient,
            identifier,
            version,
            asOf,
            birth,
            current,
            unknownReason,
            preferred,
            additional
        );
    }

    private PatientGender.Gender resolveGender(final Gender gender) {
        return gender == null
            ? null
            : new PatientGender.Gender(gender.name(), gender.display());
    }

    private PatientGender.UnknownReason resolveUnknownReason(final Tuple tuple) {
        String id = tuple.get(this.tables.patient().sexUnkReasonCd);
        String description = tuple.get(this.tables.unknownGenderReason().codeShortDescTxt);

        return id == null
            ? null
            : new PatientGender.UnknownReason(
            id,
            description
        );
    }

    private PatientGender.PreferredGender resolvePreferredGender(final Tuple tuple) {
        String id = tuple.get(this.tables.patient().preferredGenderCd);
        String description = tuple.get(this.tables.preferred().codeShortDescTxt);

        return id == null
            ? null
            : new PatientGender.PreferredGender(
            id,
            description
        );
    }

}
