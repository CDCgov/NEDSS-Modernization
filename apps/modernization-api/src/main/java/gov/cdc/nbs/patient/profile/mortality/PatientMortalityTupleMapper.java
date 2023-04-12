package gov.cdc.nbs.patient.profile.mortality;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QStateCode;
import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.country.SimpleCountryTupleMapper;
import gov.cdc.nbs.geo.state.SimpleState;
import gov.cdc.nbs.geo.state.SimpleStateTupleMapper;
import gov.cdc.nbs.message.enums.Deceased;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

class PatientMortalityTupleMapper {

    record Tables(
        QPerson patient,
        QCodeValueGeneral deceased,
        QPostalEntityLocatorParticipation locators,
        QPostalLocator address,
        QStateCode state,
        QCountryCode country
    ) {

        Tables() {
            this(
                QPerson.person,
                new QCodeValueGeneral("deceased"),
                QPostalEntityLocatorParticipation.postalEntityLocatorParticipation,
                QPostalLocator.postalLocator,
                QStateCode.stateCode,
                QCountryCode.countryCode
            );
        }
    }


    private final Tables tables;
    private final SimpleCountryTupleMapper countryMapper;
    private final SimpleStateTupleMapper stateMapper;

    PatientMortalityTupleMapper(final Tables tables) {
        this.tables = tables;
        this.countryMapper = new SimpleCountryTupleMapper(
            new SimpleCountryTupleMapper.Tables(
                this.tables.address(),
                this.tables.country()
            )
        );

        this.stateMapper = new SimpleStateTupleMapper(
            new SimpleStateTupleMapper.Tables(
                this.tables.address(),
                this.tables.state()
            )
        );

    }

    PatientMortality map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "A mortality patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "A mortality identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "A mortality version is required"
            );

        Instant asOf = tuple.get(this.tables.patient().asOfDateMorbidity);

        PatientMortality.Deceased deceased = resolveDeceased(tuple.get(this.tables.patient().deceasedIndCd));

        LocalDate deceasedOn = resolveDeceasedOn(tuple);

        String city = tuple.get(this.tables.address().cityDescTxt);

        SimpleState state = this.stateMapper.maybeMap(tuple).orElse(null);

        SimpleCountry country = this.countryMapper.maybeMap(tuple).orElse(null);

        return new PatientMortality(
            patient,
            identifier,
            version,
            asOf,
            deceased,
            deceasedOn,
            city,
            state,
            country
        );
    }

    private PatientMortality.Deceased resolveDeceased(final Deceased deceased) {
        return deceased == null
            ? null
            : new PatientMortality.Deceased(deceased.name(), deceased.display());
    }

    private LocalDate resolveDeceasedOn(final Tuple tuple) {

        Instant value = tuple.get(tables.patient().deceasedTime);

        return value == null
            ? null
            : LocalDate.ofInstant(value, ZoneId.of("UTC"));

    }
}
