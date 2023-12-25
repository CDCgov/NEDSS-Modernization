import { externalizeDate, externalizeDateTime } from 'date';
import { NameUseCd, NewPatientIdentification, NewPatientPhoneNumber, PersonInput } from 'generated/graphql/schema';
import { NewPatientEntry } from 'apps/patient/add';

function isEmpty(obj: any) {
    for (const key in obj) {
        if (obj[key]) return false;
    }
    return true;
}

export const asPersonInput = (data: NewPatientEntry) => {
    const phoneNumbers: NewPatientPhoneNumber[] = [];

    if (data.homePhone) {
        phoneNumbers.push({
            number: data.homePhone,
            type: 'PH',
            use: 'H'
        });
    }
    if (data.workPhone) {
        phoneNumbers.push({
            number: data.workPhone,
            extension: data.extension,
            type: 'PH',
            use: 'WP'
        });
    }

    data.phoneNumbers.filter((item) => item.number).forEach((item) => phoneNumbers.push(item as NewPatientPhoneNumber));

    const emailAddresses = data.emailAddresses.filter((item) => item.email).map((item) => item.email);

    const identifications = data.identification
        .filter((item) => item.type && item.value)
        .map((item) => item as NewPatientIdentification);

    const address = {
        streetAddress1: data.streetAddress1,
        streetAddress2: data.streetAddress2,
        state: data.state,
        county: data.county,
        zip: data.zip,
        censusTract: data.censusTract,
        country: data.country,
        city: data.city
    };

    const addresses = isEmpty(address) ? [] : [address];

    const name = {
        last: data.lastName,
        first: data.firstName,
        middle: data.middleName,
        suffix: data.suffix
    };

    const names = isEmpty(name) ? [] : [{ ...name, use: NameUseCd.L }];

    const payload: PersonInput = {
        asOf: externalizeDateTime(data.asOf),
        comments: data.comments,
        dateOfBirth: externalizeDate(data.dateOfBirth),
        deceasedTime: externalizeDateTime(data.deceasedTime),
        birthGender: data.birthGender,
        currentGender: data.currentGender,
        deceased: data.deceased,
        maritalStatus: data.maritalStatus,
        stateHIVCase: data.stateHIVCase,
        ethnicity: data.ethnicity,
        races: data.race,
        names,
        addresses,
        phoneNumbers,
        emailAddresses,
        identifications
    };

    return payload;
};
