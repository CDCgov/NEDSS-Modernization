package gov.cdc.nbs.patient.profile.address;

import com.querydsl.core.Tuple;

import java.time.Instant;
import java.util.Objects;


class PatientAddressTupleMapper {

    private final PatientAddressTables tables;

    PatientAddressTupleMapper(final PatientAddressTables tables) {
        this.tables = tables;
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

        Instant asOf = tuple.get(this.tables.locators().asOfDate);

        PatientAddress.Type type = mapType(tuple);

        PatientAddress.Use use = mapUse(tuple);

        String address1 = tuple.get(tables.address().streetAddr1);
        String address2 = tuple.get(tables.address().streetAddr2);
        String city = tuple.get(tables.address().cityDescTxt);

        PatientAddress.County county = maybeMapCounty(tuple);
        PatientAddress.State state = maybeMapState(tuple);

        String zipcode = tuple.get(tables.address().zipCd);

        PatientAddress.Country country = maybeMapCountry(tuple);

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

    private PatientAddress.County maybeMapCounty(final Tuple tuple) {
        String id = tuple.get(this.tables.address().cntyCd);
        String description = tuple.get(this.tables.county().codeShortDescTxt);

        return id == null
            ? null
            : new PatientAddress.County(
            id,
            description == null ? id : description
        );
    }

    private PatientAddress.State maybeMapState(final Tuple tuple) {
        String id = tuple.get(this.tables.address().stateCd);
        String description = tuple.get(this.tables.state().codeDescTxt);

        return id == null
            ? null
            : new PatientAddress.State(
            id,
            description == null ? id : description
        );
    }

    private PatientAddress.Country maybeMapCountry(final Tuple tuple) {
        String id = tuple.get(this.tables.address().cntryCd);
        String description = tuple.get(this.tables.country().codeDescTxt);

        return id == null
            ? null
            : new PatientAddress.Country(
            id,
            description == null ? id : description
        );
    }
}
