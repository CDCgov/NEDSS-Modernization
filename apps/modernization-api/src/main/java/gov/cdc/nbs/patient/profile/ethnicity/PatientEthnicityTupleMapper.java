package gov.cdc.nbs.patient.profile.ethnicity;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

class PatientEthnicityTupleMapper {

    record Tables(
        QPerson patient,
        QCodeValueGeneral ethnicGroup,
        QCodeValueGeneral unknownReason,
        QCodeValueGeneral ethnicity
    ) {
        Tables() {
            this(
                QPerson.person,
                new QCodeValueGeneral("ethnicGroup"),
                new QCodeValueGeneral("unknown_reason"),
                new QCodeValueGeneral("ethnicity_detail")
            );
        }
    }


    private final PatientEthnicityTupleMapper.Tables tables;

    PatientEthnicityTupleMapper(final PatientEthnicityTupleMapper.Tables tables) {
        this.tables = tables;
    }

    PatientEthnicity map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "An ethnicity patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(tables.patient().id),
            "An ethnicity identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "An ethnicity version is required"
            );

        LocalDate asOf = tuple.get(this.tables.patient().ethnicity.asOfDateEthnicity);

        PatientEthnicity.EthnicGroup ethnicGroup = mapEthnicGroup(tuple);

        PatientEthnicity.UnknownReason unknownReason = mapUnknownReason(tuple);

        List<PatientEthnicity.Ethnicity> detailed = mapEthnicity(tuple);

        return new PatientEthnicity(
            patient,
            identifier,
            version,
            asOf,
            ethnicGroup,
            unknownReason,
            detailed
        );
    }

    private PatientEthnicity.EthnicGroup mapEthnicGroup(final Tuple tuple) {
        String identifier = tuple.get(this.tables.ethnicGroup().id.code);
        String description = tuple.get(this.tables.ethnicGroup().codeShortDescTxt);

        return identifier == null
            ? null
            : new PatientEthnicity.EthnicGroup(
            identifier,
            description
        );
    }

    private PatientEthnicity.UnknownReason mapUnknownReason(final Tuple tuple) {
        String identifier = tuple.get(this.tables.unknownReason().id.code);
        String description = tuple.get(this.tables.unknownReason().codeShortDescTxt);

        return identifier == null
            ? null
            : new PatientEthnicity.UnknownReason(
            identifier,
            description
        );
    }

    private List<PatientEthnicity.Ethnicity> mapEthnicity(final Tuple tuple) {
        String identifier = tuple.get(this.tables.ethnicity().id.code);
        String description = tuple.get(this.tables.ethnicity().codeShortDescTxt);

        return identifier == null
            ? List.of()
            : List.of(
            new PatientEthnicity.Ethnicity(
                identifier,
                description
            )
        );

    }
}
