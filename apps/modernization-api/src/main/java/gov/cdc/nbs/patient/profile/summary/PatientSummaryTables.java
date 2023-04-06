package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.entity.odse.QEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPersonRace;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.odse.QTeleLocator;
import gov.cdc.nbs.entity.srte.QCityCodeValue;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QRaceCode;
import gov.cdc.nbs.entity.srte.QStateCode;

class PatientSummaryTables {

    private final QPerson patient;

    private final QPersonName name;

    private final QCodeValueGeneral prefix;

    private final QCodeValueGeneral ethnicity;
    private final QRaceCode race;
    private final QEntityLocatorParticipation locators;
    private final QTeleLocator phoneNumber;
    private final QTeleLocator email;
    private final QPostalLocator address;
    private final QCityCodeValue city;
    private final QStateCode state;
    private final QCountryCode country;

    PatientSummaryTables() {
        patient = QPerson.person;
        name = QPersonName.personName;
        prefix = new QCodeValueGeneral("prefix");
        ethnicity = new QCodeValueGeneral("ethnicity");
        race = QRaceCode.raceCode;
        locators = QEntityLocatorParticipation.entityLocatorParticipation;
        phoneNumber = new QTeleLocator("phone_number");
        email = new QTeleLocator("email");
        address = QPostalLocator.postalLocator;
        city = QCityCodeValue.cityCodeValue;
        state = QStateCode.stateCode;
        country = QCountryCode.countryCode;
    }

    QPerson patient() {
        return patient;
    }

    QCodeValueGeneral ethnicity() {
        return ethnicity;
    }

    public QPersonName name() {
        return name;
    }

    public QCodeValueGeneral prefix() {
        return prefix;
    }

    QRaceCode race() {
        return race;
    }

    QEntityLocatorParticipation locators() {
        return locators;
    }

    QTeleLocator phoneNumber() {
        return phoneNumber;
    }

    QTeleLocator email() {
        return email;
    }

    QPostalLocator address() {
        return address;
    }

    QCityCodeValue city() {
        return city;
    }

    QStateCode state() {
        return state;
    }

    QCountryCode country() {
        return country;
    }
}
