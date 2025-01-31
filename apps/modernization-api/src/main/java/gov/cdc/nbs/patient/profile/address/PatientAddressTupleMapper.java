package gov.cdc.nbs.patient.profile.address;

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

import java.time.LocalDate;
import java.util.Objects;


class PatientAddressTupleMapper {

    record Tables(
        QPerson patient,
        QPostalEntityLocatorParticipation locators,
        QPostalLocator address,
        QCodeValueGeneral type,
        QCodeValueGeneral use,
        QStateCountyCodeValue county,
        QStateCode state,
        QCountryCode country
    ) {

        Tables() {
            this(
                QPerson.person,
                QPostalEntityLocatorParticipation.postalEntityLocatorParticipation,
                QPostalLocator.postalLocator,
                new QCodeValueGeneral("type"),
                new QCodeValueGeneral("use"),
                QStateCountyCodeValue.stateCountyCodeValue,
                QStateCode.stateCode,
                QCountryCode.countryCode
            );
        }

    }


    private final Tables tables;
    private final SimpleCountyTupleMapper countyMapper;
    private final SimpleStateTupleMapper stateMapper;
    private final SimpleCountryTupleMapper countryMapper;

    PatientAddressTupleMapper(final Tables tables) {
        this.tables = tables;

        this.countyMapper = new SimpleCountyTupleMapper(
            new SimpleCountyTupleMapper.Tables(
                this.tables.address(),
                this.tables.county()
            )
        );

        this.stateMapper = new SimpleStateTupleMapper(
            new SimpleStateTupleMapper.Tables(
                this.tables.address(),
                this.tables.state()
            )
        );

        this.countryMapper = new SimpleCountryTupleMapper(
            new SimpleCountryTupleMapper.Tables(
                this.tables.address(),
                this.tables.country()
            )
        );
    }

    PatientAddress map(final Tuple tuple) {

        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "An address patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.locators().id.locatorUid),
            "An address identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.locators().versionCtrlNbr),
                "An address version is required"
            );

        LocalDate asOf = tuple.get(this.tables.locators().asOfDate);

        PatientAddress.Type type = mapType(tuple);

        PatientAddress.Use use = mapUse(tuple);

        String address1 = tuple.get(tables.address().streetAddr1);
        String address2 = tuple.get(tables.address().streetAddr2);
        String city = tuple.get(tables.address().cityDescTxt);

        SimpleCounty county = this.countyMapper.maybeMap(tuple).orElse(null);
        SimpleState state = this.stateMapper.maybeMap(tuple).orElse(null);

        String zipcode = tuple.get(tables.address().zipCd);

        SimpleCountry country = this.countryMapper.maybeMap(tuple).orElse(null);

        String censusTract = tuple.get(tables.address().censusTract);
        String comment = tuple.get(tables.locators().locatorDescTxt);

        return new PatientAddress(
            patient,
            identifier,
            version,
            asOf,
            type,
            use,
            address1,
            address2,
            city,
            county,
            state,
            zipcode,
            country,
            censusTract,
            comment
        );
    }

    private PatientAddress.Type mapType(final Tuple tuple) {
        String id = tuple.get(this.tables.locators().cd);
        String description = tuple.get(this.tables.type().codeShortDescTxt);

        return new PatientAddress.Type(
            id,
            description
        );
    }

    private PatientAddress.Use mapUse(final Tuple tuple) {
        String id = tuple.get(this.tables.locators().useCd);
        String description = tuple.get(this.tables.use().codeShortDescTxt);

        return new PatientAddress.Use(
            id,
            description
        );
    }

}
