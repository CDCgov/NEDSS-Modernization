package gov.cdc.nbs.patient.profile.address;

import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QStateCode;

record PatientAddressTables(
    QPerson patient,
    QPostalEntityLocatorParticipation locators,
    QPostalLocator address,
    QCodeValueGeneral type,
    QCodeValueGeneral use,
    QCodeValueGeneral county,
    QStateCode state,
    QCountryCode country
) {

    PatientAddressTables() {
        this(
            QPerson.person,
            QPostalEntityLocatorParticipation.postalEntityLocatorParticipation,
            QPostalLocator.postalLocator,
            new QCodeValueGeneral("type"),
            new QCodeValueGeneral("use"),
            new QCodeValueGeneral("county"),
            QStateCode.stateCode,
            QCountryCode.countryCode
        );
    }

}
