package gov.cdc.nbs.patient.profile.general;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QLanguageCode;
import gov.cdc.nbs.entity.srte.QNaicsIndustryCode;
import gov.cdc.nbs.message.enums.Indicator;
import gov.cdc.nbs.patient.IndicatorStringConverter;

import java.time.Instant;
import java.util.Objects;

class PatientGeneralTupleMapper {

    record Tables(
        QPerson patient,
        QCodeValueGeneral maritalStatus,
        QNaicsIndustryCode occupation,
        QCodeValueGeneral education,
        QLanguageCode language
    ) {

        Tables() {
            this(
                QPerson.person,
                new QCodeValueGeneral("marital_status"),
                QNaicsIndustryCode.naicsIndustryCode,
                new QCodeValueGeneral("education"),
                QLanguageCode.languageCode
            );
        }
    }


    private final Tables tables;

    PatientGeneralTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    PatientGeneral map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "A general patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "A general identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "A general version is required"
            );

        Instant asOf = tuple.get(this.tables.patient().asOfDateGeneral);

        PatientGeneral.MaritalStatus maritalStatus = resolveMaritalStatus(tuple);

        String maternalMaidenName = tuple.get(this.tables.patient().mothersMaidenNm);
        Short adultsInHouse = tuple.get(this.tables.patient().adultsInHouseNbr);
        Short childrenInHouse = tuple.get(this.tables.patient().childrenInHouseNbr);

        PatientGeneral.Occupation occupation = resolveOccupation(tuple);

        PatientGeneral.EducationLevel educationLevel = resolveEducationLevel(tuple);

        PatientGeneral.Language primaryLanguage = resolveLanguage(tuple);

        Indicator speaksEnglish = IndicatorStringConverter.fromString(tuple.get(this.tables.patient().speaksEnglishCd));

        String stateHIVCase = tuple.get(this.tables.patient().eharsId);

        return new PatientGeneral(
            patient,
            identifier,
            version,
            asOf,
            maritalStatus,
            maternalMaidenName,
            adultsInHouse,
            childrenInHouse,
            occupation,
            educationLevel,
            primaryLanguage,
            speaksEnglish,
            stateHIVCase
        );
    }

    private PatientGeneral.MaritalStatus resolveMaritalStatus(final Tuple tuple) {
        String id = tuple.get(this.tables.maritalStatus().id.code);
        String description = tuple.get(this.tables.maritalStatus().codeShortDescTxt);

        return id == null
            ? null
            : new PatientGeneral.MaritalStatus(
            id,
            description
        );
    }

    private PatientGeneral.Occupation resolveOccupation(final Tuple tuple) {
        String id = tuple.get(this.tables.occupation().id);
        String description = tuple.get(this.tables.occupation().codeShortDescTxt);

        return id == null
            ? null
            : new PatientGeneral.Occupation(
            id,
            description
        );
    }

    private PatientGeneral.EducationLevel resolveEducationLevel(final Tuple tuple) {
        String id = tuple.get(this.tables.education().id.code);
        String description = tuple.get(this.tables.education().codeShortDescTxt);

        return id == null
            ? null
            : new PatientGeneral.EducationLevel(
            id,
            description
        );
    }

    private PatientGeneral.Language resolveLanguage(final Tuple tuple) {
        String id = tuple.get(this.tables.language().id);
        String description = tuple.get(this.tables.language().codeShortDescTxt);

        return id == null
            ? null
            : new PatientGeneral.Language(
            id,
            description
        );
    }
}
