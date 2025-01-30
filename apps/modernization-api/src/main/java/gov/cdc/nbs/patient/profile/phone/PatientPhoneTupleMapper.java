package gov.cdc.nbs.patient.profile.phone;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QTeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QTeleLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;

import java.time.LocalDate;
import java.util.Objects;

class PatientPhoneTupleMapper {

    record Tables(
        QPerson patient,
        QTeleEntityLocatorParticipation locators,
        QCodeValueGeneral type,
        QCodeValueGeneral use,
        QTeleLocator phoneNumber
    ) {

        Tables() {
            this(
                QPerson.person,
                QTeleEntityLocatorParticipation.teleEntityLocatorParticipation,
                new QCodeValueGeneral("type"),
                new QCodeValueGeneral("use"),
                QTeleLocator.teleLocator
            );
        }

    }


    private final Tables tables;

    PatientPhoneTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    PatientPhone map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "A phone patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.locators().id.locatorUid),
            "A phone identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.locators().versionCtrlNbr),
                "A phone version is required"
            );

        LocalDate asOf = tuple.get(this.tables.locators().asOfDate);

        PatientPhone.Type type = mapType(tuple);

        PatientPhone.Use use = mapUse(tuple);

        String countryCode = tuple.get(tables.phoneNumber().cntryCd);
        String number = tuple.get(tables.phoneNumber().phoneNbrTxt);
        String extension = tuple.get(tables.phoneNumber().extensionTxt);

        String email = tuple.get(tables.phoneNumber().emailAddress);
        String url = tuple.get(tables.phoneNumber().urlAddress);

        String comment = tuple.get(tables.locators().locatorDescTxt);

        return new PatientPhone(
            patient,
            identifier,
            version,
            asOf,
            type,
            use,
            countryCode,
            number,
            extension,
            email,
            url,
            comment
        );
    }

    private PatientPhone.Type mapType(final Tuple tuple) {
        String id = tuple.get(this.tables.locators().cd);
        String description = tuple.get(this.tables.type().codeShortDescTxt);

        return new PatientPhone.Type(
            id,
            description
        );
    }

    private PatientPhone.Use mapUse(final Tuple tuple) {
        String id = tuple.get(this.tables.locators().useCd);
        String description = tuple.get(this.tables.use().codeShortDescTxt);

        return new PatientPhone.Use(
            id,
            description
        );
    }
}
