package gov.cdc.nbs.patient.profile.mortality;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QStateCode;
import gov.cdc.nbs.entity.srte.QStateCountyCodeValue;
import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.country.SimpleCountryTupleMapper;
import gov.cdc.nbs.geo.county.SimpleCounty;
import gov.cdc.nbs.geo.county.SimpleCountyTupleMapper;
import gov.cdc.nbs.geo.state.SimpleState;
import gov.cdc.nbs.geo.state.SimpleStateTupleMapper;
import gov.cdc.nbs.message.enums.Deceased;

import java.time.LocalDate;
import java.util.Objects;

class PatientMortalityTupleMapper {

    record Tables(
        QPerson patient,
        QCodeValueGeneral deceased,
        QPostalEntityLocatorParticipation locators,
        QPostalLocator address,
        QStateCode state,
        QStateCountyCodeValue county,
        QCountryCode country
    ) {

        Tables() {
            this(
                QPerson.person,
                new QCodeValueGeneral("deceased"),
                QPostalEntityLocatorParticipation.postalEntityLocatorParticipation,
                QPostalLocator.postalLocator,
                QStateCode.stateCode,
                QStateCountyCodeValue.stateCountyCodeValue,
                QCountryCode.countryCode
            );
        }
    }


    private final Tables tables;
    private final SimpleCountryTupleMapper countryMapper;
    private final SimpleStateTupleMapper stateMapper;
    private final SimpleCountyTupleMapper countyMapper;

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

        this.countyMapper = new SimpleCountyTupleMapper(
            new SimpleCountyTupleMapper.Tables(
                this.tables.address(),
                this.tables.county()
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

        LocalDate asOf = tuple.get(this.tables.patient().asOfDateMorbidity);

        PatientMortality.Deceased deceased = resolveDeceased(tuple.get(this.tables.patient().deceasedIndCd));

        LocalDate deceasedOn = tuple.get(tables.patient().deceasedTime);

        String city = tuple.get(this.tables.address().cityDescTxt);

        SimpleState state = this.stateMapper.maybeMap(tuple).orElse(null);

        SimpleCounty county = this.countyMapper.maybeMap(tuple).orElse(null);

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
            county,
            country
        );
    }

    private PatientMortality.Deceased resolveDeceased(final Deceased deceased) {
        return deceased == null
            ? null
            : new PatientMortality.Deceased(deceased.name(), deceased.display());
    }

}
