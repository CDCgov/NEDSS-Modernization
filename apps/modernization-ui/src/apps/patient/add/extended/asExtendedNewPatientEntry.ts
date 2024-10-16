import { asSelectable, findByValue, Selectable } from 'options';
import { NewPatientEntry } from '../NewPatientEntry';
import { ExtendedNewPatientEntry } from './entry';
import { AddressEntry, IdentificationEntry, NameEntry, PhoneEmailEntry } from 'apps/patient/data/entry';
import { PatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { CodedValue } from 'coded';
import { isEmpty } from 'utils/isEmpty';
import { CountiesCodedValues, LocationCodedValues } from 'location';
import { RaceEntry } from 'apps/patient/data/race/entry';

const asExtendedNewPatientEntry = (
    initial: NewPatientEntry,
    nameCodes: PatientNameCodedValues,
    addressCodes: LocationCodedValues,
    raceCodes: Selectable[],
    counties?: CountiesCodedValues
): ExtendedNewPatientEntry => {
    const extendedFormValues: ExtendedNewPatientEntry = {
        administrative: { asOf: initial.asOf, comment: initial.comments ?? undefined },
        names: nameExtended(initial, nameCodes),
        addresses: addressExtended(initial, addressCodes, counties),
        phoneEmails: phoneEmailsExtended(initial),
        races: raceExtended(initial, raceCodes),
        identifications: identificationExtended(initial),
        ethnicity: { asOf: initial.asOf, ethnicGroup: asSelectable(initial.ethnicity ?? ''), detailed: [] },
        birthAndSex: {
            asOf: initial.asOf,
            bornOn: initial.dateOfBirth ?? undefined,
            sex: initial.birthGender ? asSelectable(initial.birthGender) : undefined,
            current: initial.currentGender ? asSelectable(initial.currentGender) : undefined
        },
        mortality: {
            asOf: initial.asOf,
            deceased: initial.deceased ? asSelectable(initial.deceased) : undefined,
            deceasedOn: initial.deceasedTime ?? undefined
        },
        general: {
            asOf: initial.asOf,
            maritalStatus: initial.maritalStatus ? asSelectable(initial.maritalStatus) : undefined,
            stateHIVCase: initial.stateHIVCase ?? undefined
        }
    };
    return extendedFormValues;
};

const nameExtended = (initial: NewPatientEntry, nameCodes: PatientNameCodedValues): NameEntry[] | undefined => {
    const suffix: CodedValue | undefined = initial.suffix
        ? nameCodes.suffixes.find((suf) => suf.value === initial.suffix)
        : undefined;

    if (
        !isEmpty({
            first: initial.firstName,
            last: initial.lastName,
            middle: initial.middleName,
            suffix: initial.suffix
        })
    ) {
        return [
            {
                asOf: initial.asOf,
                type: asSelectable('L', 'Legal'),
                first: initial.firstName ?? undefined,
                last: initial.lastName ?? undefined,
                middle: initial.middleName ?? undefined,
                suffix: suffix ? asSelectable(suffix.value, suffix.name) : undefined
            }
        ];
    }
};

const addressExtended = (
    initial: NewPatientEntry,
    addressCodes: LocationCodedValues,
    counties?: CountiesCodedValues
): AddressEntry[] | undefined => {
    const state = initial.state ? addressCodes.states.all.find((state) => state.value === initial.state) : undefined;
    const country = initial.country
        ? addressCodes.countries.find((country) => country.value === initial.country)
        : undefined;
    const county = initial.county ? counties?.counties.find((county) => county.value === initial.county) : undefined;
    if (
        !isEmpty({
            address1: initial.streetAddress1,
            address2: initial.streetAddress2,
            city: initial.city,
            state: initial.state,
            zipcode: initial.zip,
            county: initial.county,
            country: initial.country
        })
    ) {
        return [
            {
                asOf: initial.asOf,
                type: asSelectable('H', 'House'),
                use: asSelectable('H', 'Home'),
                address1: initial.streetAddress1 ?? undefined,
                address2: initial.streetAddress2 ?? undefined,
                city: initial.city ?? undefined,
                state: state ? asSelectable(state.value, state.name) : undefined,
                zipcode: initial.zip ?? undefined,
                county: county ? asSelectable(county.value, county.name) : undefined,
                country: country ? asSelectable(country.value, country.name) : undefined,
                censusTract: initial.censusTract ?? undefined
            }
        ];
    }
};

const phoneEmailsExtended = (initial: NewPatientEntry): PhoneEmailEntry[] | undefined => {
    const phoneEmails: PhoneEmailEntry[] = [];

    if (
        !isEmpty({
            home: initial.homePhone,
            work: initial.workPhone,
            cell: initial.cellPhone
        }) ||
        initial.emailAddresses.length > 0
    ) {
        if (initial.homePhone) {
            phoneEmails.push({
                asOf: initial.asOf,
                type: asSelectable('PH', 'Phone'),
                use: asSelectable('H', 'Home'),
                phoneNumber: initial.homePhone
            });
        }

        if (initial.cellPhone) {
            phoneEmails.push({
                asOf: initial.asOf,
                type: asSelectable('CP', 'Cellular phone'),
                use: asSelectable('MC', 'Mobile contact'),
                phoneNumber: initial.cellPhone
            });
        }

        if (initial.workPhone) {
            phoneEmails.push({
                asOf: initial.asOf,
                type: asSelectable('PH', 'Phone'),
                use: asSelectable('WP', 'Primary work place'),
                phoneNumber: initial.workPhone
            });
        }

        if (initial.emailAddresses.length) {
            initial.emailAddresses.map((emailAddress) => {
                if (emailAddress.email != null) {
                    phoneEmails.push({
                        asOf: initial.asOf,
                        type: asSelectable('NET', 'Email address'),
                        use: asSelectable('H', 'Home'),
                        email: emailAddress.email ?? ''
                    });
                }
            });
        }
        return phoneEmails;
    }
};

const identificationExtended = (initial: NewPatientEntry): IdentificationEntry[] | undefined => {
    const identifications: IdentificationEntry[] = [];

    if (initial.identification.length > 0) {
        initial.identification.map((ident) => {
            if (!isEmpty({ issuer: ident.authority, id: ident.value, type: ident.type })) {
                identifications.push({
                    asOf: initial.asOf,
                    type: asSelectable(ident.type ?? ''),
                    issuer: asSelectable(ident.authority ?? ''),
                    id: ident.value ?? ''
                });
            }
        });
        return identifications;
    }
};

const raceExtended = (initial: NewPatientEntry, raceCodes: Selectable[]): RaceEntry[] | undefined => {
    const races: RaceEntry[] = [];
    const raceResolver = findByValue(raceCodes);

    if (initial.race?.length ?? 0 > 0) {
        initial.race?.map((race) => {
            races.push({
                id: new Date().getTime(),
                asOf: initial.asOf,
                race: raceResolver(race) ?? asSelectable(''),
                detailed: []
            });
        });
        return races;
    }
};

export { asExtendedNewPatientEntry };
