import { asSelectable, findByValue, Selectable } from 'options';
import { PatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { IdentificationEntry } from 'apps/patient/data/entry';
import { RaceEntry, AddressEntry, NameEntry, PhoneEmailEntry } from 'apps/patient/data';
import { NewPatientEntry } from 'apps/patient/add';
import { ExtendedNewPatientEntry } from './entry';
import { CodedValue } from 'coded';
import { isEmpty, Mapping } from 'utils';
import { LEGAL } from 'options/name/types';
import { HOME as HOME_ADDRESS } from 'options/address/uses';
import { HOUSE } from 'options/address/types';
import { CELL_PHONE, PHONE, EMAIL } from 'options/phone/types';
import { HOME as HOME_PHONE, MOBILE_CONTACT, PRIMARY_WORKPLACE } from 'options/phone/uses';

const mapOr =
    <R, S, O>(mapping: Mapping<R, S>, fallback: O) =>
    (value?: R | null): S | O =>
        value ? mapping(value) : fallback;

const asSelectableIfPresent = mapOr(asSelectable, undefined);

const maybeSelectable = mapOr(asSelectable, null);

const asExtendedNewPatientEntry = (
    initial: NewPatientEntry,
    nameCodes: PatientNameCodedValues,
    raceCodes: Selectable[]
): ExtendedNewPatientEntry => {
    return {
        administrative: { asOf: initial.asOf, comment: initial.comments ?? undefined },
        names: nameExtended(initial, nameCodes),
        addresses: addressExtended(initial),
        phoneEmails: phoneEmailsExtended(initial),
        races: raceExtended(initial, raceCodes),
        identifications: identificationExtended(initial),
        ethnicity: { asOf: initial.asOf, ethnicGroup: maybeSelectable(initial.ethnicity), detailed: [] },
        birthAndSex: {
            asOf: initial.asOf,
            bornOn: initial.dateOfBirth ?? undefined,
            sex: asSelectableIfPresent(initial.birthGender),
            current: asSelectableIfPresent(initial.currentGender)
        },
        mortality: {
            asOf: initial.asOf,
            deceased: asSelectableIfPresent(initial.deceased),
            deceasedOn: initial.deceasedTime ?? undefined
        },
        general: {
            asOf: initial.asOf,
            maritalStatus: asSelectableIfPresent(initial.maritalStatus),
            stateHIVCase: initial.stateHIVCase ?? undefined
        }
    };
};

const nameExtended = (initial: NewPatientEntry, nameCodes: PatientNameCodedValues): NameEntry[] => {
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
                type: LEGAL,
                first: initial.firstName ?? undefined,
                last: initial.lastName ?? undefined,
                middle: initial.middleName ?? undefined,
                suffix
            }
        ];
    }
    return [];
};

const addressExtended = (initial: NewPatientEntry): AddressEntry[] => {
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
                type: HOUSE,
                use: HOME_ADDRESS,
                address1: initial.streetAddress1 ?? undefined,
                address2: initial.streetAddress2 ?? undefined,
                city: initial.city ?? undefined,
                state: initial.state,
                zipcode: initial.zip ?? undefined,
                county: initial.county,
                country: initial.country,
                censusTract: initial.censusTract ?? undefined
            }
        ];
    }

    return [];
};

const phoneEmailsExtended = (initial: NewPatientEntry): PhoneEmailEntry[] => {
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
                type: PHONE,
                use: HOME_PHONE,
                phoneNumber: initial.homePhone
            });
        }

        if (initial.cellPhone) {
            phoneEmails.push({
                asOf: initial.asOf,
                type: CELL_PHONE,
                use: MOBILE_CONTACT,
                phoneNumber: initial.cellPhone
            });
        }

        if (initial.workPhone) {
            phoneEmails.push({
                asOf: initial.asOf,
                type: PHONE,
                use: PRIMARY_WORKPLACE,
                phoneNumber: initial.workPhone,
                extension: initial.extension ?? undefined
            });
        }
        initial.emailAddresses.map((emailAddress) => {
            if (!isEmpty(emailAddress)) {
                phoneEmails.push({
                    asOf: initial.asOf,
                    type: EMAIL,
                    use: HOME_PHONE,
                    email: emailAddress.email ?? undefined
                });
            }
        });

        return phoneEmails;
    }

    return [];
};

const identificationExtended = (initial: NewPatientEntry): IdentificationEntry[] => {
    const identifications: IdentificationEntry[] = [];

    if (initial.identification.length > 0) {
        initial.identification.map((identity) => {
            if (!isEmpty({ issuer: identity.authority, id: identity.value, type: identity.type })) {
                identifications.push({
                    asOf: initial.asOf,
                    type: maybeSelectable(identity.type),
                    issuer: maybeSelectable(identity.authority),
                    id: identity.value ?? null
                });
            }
        });
    }
    return identifications;
};

const raceExtended = (initial: NewPatientEntry, raceCodes: Selectable[]): RaceEntry[] => {
    const races: RaceEntry[] = [];
    const raceResolver = findByValue(raceCodes);

    if (initial.race?.length ?? 0 > 0) {
        initial.race?.map((race) => {
            races.push({
                id: new Date().getTime(),
                asOf: initial.asOf,
                race: raceResolver(race) ?? null,
                detailed: []
            });
        });
    }
    return races;
};

export { asExtendedNewPatientEntry };
