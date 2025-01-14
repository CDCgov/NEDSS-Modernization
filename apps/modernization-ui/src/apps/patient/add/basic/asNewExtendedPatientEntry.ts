import { ExtendedNewPatientEntry } from '../extended';
import { BasicNewPatientEntry } from './entry';
import { AddressEntry, IdentificationEntry, NameEntry, PhoneEmailEntry, RaceEntry } from 'apps/patient/data';
import { LEGAL } from 'options/name/types';
import { HOME as HOME_ADDRESS } from 'options/address/uses';
import { HOUSE } from 'options/address/types';
import { CELL_PHONE, PHONE, EMAIL } from 'options/phone/types';
import { HOME as HOME_PHONE, MOBILE_CONTACT, PRIMARY_WORKPLACE } from 'options/phone/uses';
import { isEmpty } from 'utils';

const asNewExtendedPatientEntry = (initial: BasicNewPatientEntry): ExtendedNewPatientEntry => {
    return {
        administrative: initial.administrative,
        names: nameExtended(initial),
        addresses: addressExtended(initial),
        phoneEmails: phoneEmailsExtended(initial),
        races: raceExtended(initial),
        identifications: identificationExtended(initial),
        ethnicity: { asOf: initial.administrative.asOf, ethnicGroup: initial.ethnicityRace?.ethnicity, detailed: [] },
        birthAndSex: {
            asOf: initial.administrative.asOf,
            bornOn: initial.personalDetails?.bornOn,
            sex: initial.personalDetails?.birthSex,
            current: initial.personalDetails?.currentSex
        },
        mortality: {
            asOf: initial.administrative.asOf,
            deceased: initial.personalDetails?.deceased,
            deceasedOn: initial.personalDetails?.deceasedOn
        },
        general: {
            asOf: initial.administrative.asOf,
            maritalStatus: initial.personalDetails?.maritalStatus,
            stateHIVCase: initial.personalDetails?.stateHIVCase
        }
    };
};

const identificationExtended = (initial: BasicNewPatientEntry): IdentificationEntry[] => {
    const identifications: IdentificationEntry[] = [];

    if (initial.identifications?.length ?? 0 > 0) {
        initial.identifications?.map((identity) => {
            if (!isEmpty({ issuer: identity.issuer, id: identity.id, type: identity.type })) {
                identifications.push({
                    asOf: initial.administrative.asOf,
                    type: identity.type,
                    issuer: identity.issuer,
                    id: identity.id
                });
            }
        });
    }
    return identifications;
};

const raceExtended = (initial: BasicNewPatientEntry): RaceEntry[] => {
    const races: RaceEntry[] = [];

    if (initial.ethnicityRace?.races?.length ?? 0 > 0) {
        initial.ethnicityRace?.races?.map((race) => {
            races.push({
                id: new Date().getTime(),
                asOf: initial.administrative.asOf,
                race: race ?? null,
                detailed: []
            });
        });
    }
    return races;
};

const phoneEmailsExtended = (initial: BasicNewPatientEntry): PhoneEmailEntry[] => {
    const phoneEmails: PhoneEmailEntry[] = [];
    if (
        !isEmpty({
            home: initial.phoneEmail?.home,
            workNum: initial.phoneEmail?.work?.phone,
            workExt: initial.phoneEmail?.work?.extension,
            cell: initial.phoneEmail?.cell,
            email: initial.phoneEmail?.email
        })
    ) {
        if (initial.phoneEmail?.home) {
            phoneEmails.push({
                asOf: initial.administrative.asOf,
                type: PHONE,
                use: HOME_PHONE,
                phoneNumber: initial.phoneEmail.home
            });
        }

        if (initial.phoneEmail?.cell) {
            phoneEmails.push({
                asOf: initial.administrative.asOf,
                type: CELL_PHONE,
                use: MOBILE_CONTACT,
                phoneNumber: initial.phoneEmail.cell
            });
        }

        if (initial.phoneEmail?.work) {
            phoneEmails.push({
                asOf: initial.administrative.asOf,
                type: PHONE,
                use: PRIMARY_WORKPLACE,
                phoneNumber: initial.phoneEmail.work.phone,
                extension: initial.phoneEmail.work.extension ?? undefined
            });
        }

        if (initial.phoneEmail?.email) {
            phoneEmails.push({
                asOf: initial.administrative.asOf,
                type: EMAIL,
                use: HOME_PHONE,
                email: initial.phoneEmail.email ?? undefined
            });
        }

        return phoneEmails;
    }

    return [];
};

const nameExtended = (initial: BasicNewPatientEntry): NameEntry[] => {
    if (
        !isEmpty({
            first: initial.name?.first,
            last: initial.name?.last,
            middle: initial.name?.middle,
            suffix: initial.name?.suffix
        })
    ) {
        return [
            {
                asOf: initial.administrative.asOf,
                type: LEGAL,
                first: initial.name?.first,
                last: initial.name?.last,
                middle: initial.name?.middle,
                suffix: initial.name?.suffix
            }
        ];
    }
    return [];
};

const addressExtended = (initial: BasicNewPatientEntry): AddressEntry[] => {
    if (
        !isEmpty({
            address1: initial.address?.address1,
            address2: initial.address?.address2,
            city: initial.address?.city,
            state: initial.address?.state,
            zipcode: initial.address?.zipcode,
            county: initial.address?.county,
            country: initial.address?.country
        })
    ) {
        return [
            {
                asOf: initial.administrative.asOf,
                type: HOUSE,
                use: HOME_ADDRESS,
                address1: initial.address?.address1 ?? undefined,
                address2: initial.address?.address2 ?? undefined,
                city: initial.address?.city ?? undefined,
                state: initial.address?.state,
                zipcode: initial.address?.zipcode ?? undefined,
                county: initial.address?.county,
                country: initial.address?.country,
                censusTract: initial.address?.censusTract ?? undefined
            }
        ];
    }

    return [];
};

export { asNewExtendedPatientEntry };
