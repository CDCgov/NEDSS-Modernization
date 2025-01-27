import { externalizeDate } from 'date';
import { NameUseCd, NewPatientIdentification, NewPatientPhoneNumber, PersonInput } from 'generated/graphql/schema';
import { EmailEntry, NewPatientEntry } from 'apps/patient/add';
import { maybeMapAll } from 'utils/mapping';

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
    if (data.cellPhone) {
        phoneNumbers.push({
            number: data.cellPhone,
            extension: data.extension,
            type: 'CP',
            use: 'MC'
        });
    }

    data.phoneNumbers.filter((item) => item.number).forEach((item) => phoneNumbers.push(item as NewPatientPhoneNumber));

    const emailAddresses = maybeMapAll((item: EmailEntry) => item.email)(data.emailAddresses);

    const identifications = data.identification
        .filter((item) => item.type && item.value)
        .map((item) => item as NewPatientIdentification);

    const address = {
        streetAddress1: data.streetAddress1,
        streetAddress2: data.streetAddress2,
        state: data.state?.value,
        county: data.county?.value,
        zip: data.zip,
        censusTract: data.censusTract,
        country: data.country?.value,
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
        asOf: externalizeDate(data.asOf),
        comments: data.comments,
        dateOfBirth: externalizeDate(data.dateOfBirth),
        deceasedTime: externalizeDate(data.deceasedTime),
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
