import { asSelectable, Selectable } from 'options';
import { NewPatientEntry } from '../NewPatientEntry';
import { ExtendedNewPatientEntry } from './entry';
import { AddressEntry, IdentificationEntry, NameEntry, PhoneEmailEntry, RaceEntry } from 'apps/patient/data/entry';
import { today } from 'date/today';
import { PatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { CodedValue } from 'coded';
import { PatientAddressCodedValues } from 'apps/patient/profile/addresses/usePatientAddressCodedValues';

const useExtendedTransformation = (
    initial: NewPatientEntry,
    nameCodes: PatientNameCodedValues,
    addressCodes: PatientAddressCodedValues,
    raceCodes: Selectable[]
): ExtendedNewPatientEntry => {
    const extendedFormValues: ExtendedNewPatientEntry = {
        administrative: { asOf: initial.asOf ?? today(), comment: initial.comments ?? undefined },
        names: nameExtended(initial, nameCodes),
        // addresses: addressExtended(initial, addressCodes),
        // phoneEmails: phoneEmailsExtended(initial),
        // races: raceExtended(initial, raceCodes),
        // identifications: identificationExtended(initial),
        ethnicity: { asOf: initial.asOf ?? today(), ethnicity: asSelectable(initial.ethnicity ?? ''), detailed: [] },
        birthAndSex: {
            asOf: initial.asOf ?? today(),
            bornOn: initial.dateOfBirth ?? '',
            sex: asSelectable(initial.birthGender ?? ''),
            current: asSelectable(initial.currentGender ?? '')
        },
        mortality: {
            asOf: initial.asOf ?? today(),
            deceased: asSelectable(initial.deceased ?? ''),
            deceasedOn: initial.deceasedTime ?? ''
        },
        general: {
            asOf: initial.asOf ?? today(),
            maritalStatus: asSelectable(initial.maritalStatus ?? ''),
            stateHIVCase: initial.stateHIVCase ?? ''
        }
    };
    console.log({ extendedFormValues });
    return extendedFormValues;
};

const nameExtended = (initial: NewPatientEntry, nameCodes: PatientNameCodedValues): NameEntry[] => {
    const suffix: CodedValue | undefined = initial.suffix
        ? nameCodes.suffixes.find((suf) => suf.value === initial.suffix)
        : undefined;

    console.log({ suffix });

    const name: NameEntry | undefined = {
        asOf: initial.asOf ?? today(),
        type: asSelectable('L', 'Legal'),
        first: initial.firstName ?? undefined,
        last: initial.lastName ?? undefined,
        middle: initial.middleName ?? undefined,
        suffix: suffix ? asSelectable(suffix.value, suffix.name) : undefined
    };

    return [name];
};

const addressExtended = (initial: NewPatientEntry, addressCodes: PatientAddressCodedValues): AddressEntry[] => {
    // const location = useLocationCodedValues();
    // const state = initial.state ? location.states.all.find((state) => state.value === initial.state) : undefined;

    const addresses = {
        asOf: initial.asOf ?? today(),
        type: asSelectable('H', 'House'),
        use: asSelectable('H', 'Home'),
        address1: initial.streetAddress1 ?? undefined,
        address2: initial.streetAddress2 ?? undefined,
        city: initial.city ?? undefined,
        // state: state ? asSelectable(state.value, state.name) : undefined,
        zipcode: initial.zip ?? undefined,
        county: asSelectable(initial.county ?? ''),
        country: asSelectable(initial.country ?? ''),
        censusTract: initial.censusTract ?? undefined
    };
    return [addresses];
};

const phoneEmailsExtended = (initial: NewPatientEntry): PhoneEmailEntry[] => {
    const phoneEmails: PhoneEmailEntry[] = [];

    if (initial.homePhone) {
        phoneEmails.push({
            asOf: initial.asOf ?? today(),
            type: asSelectable('PH', 'Phone'),
            use: asSelectable('H', 'Home'),
            phoneNumber: initial.homePhone
        });
    }

    if (initial.cellPhone) {
        phoneEmails.push({
            asOf: initial.asOf ?? today(),
            type: asSelectable('CP', 'Cellular phone'),
            use: asSelectable('MC', 'Mobile contact'),
            phoneNumber: initial.cellPhone
        });
    }

    if (initial.workPhone) {
        phoneEmails.push({
            asOf: initial.asOf ?? today(),
            type: asSelectable('PH', 'Phone'),
            use: asSelectable('WP', 'Primary work place'),
            phoneNumber: initial.workPhone
        });
    }

    return phoneEmails;
};

const identificationExtended = (initial: NewPatientEntry): IdentificationEntry[] => {
    const identifications: IdentificationEntry[] = [];
    initial.identification.map((ident) => {
        identifications.push({
            asOf: initial.asOf ?? today(),
            type: asSelectable(ident.type ?? ''),
            issuer: asSelectable(ident.authority ?? ''),
            id: ident.value ?? ''
        });
    });

    return identifications;
};

const raceExtended = (initial: NewPatientEntry, raceCodes: Selectable[]): RaceEntry[] => {
    const races: RaceEntry[] = [];

    initial.race?.map((race) => {
        races.push({
            asOf: initial.asOf ?? today(),
            race: asSelectable(race),
            detailed: []
        });
    });

    return races;
};

export { useExtendedTransformation };
