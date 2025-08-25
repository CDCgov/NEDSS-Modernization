import { internalizeDate, today } from 'date';
import { asSelectable, isEqual, Selectable } from 'options';
import { exists, isEmpty, orNull, orUndefined } from 'utils';
import { not } from 'utils/predicate';
import { mapOr, maybeMapAll, maybeMapEach } from 'utils/mapping';
import { LEGAL } from 'options/name/types';
import { HOME as HOME_ADDRESS } from 'options/address/uses';
import { HOUSE } from 'options/address/types';
import { CELL_PHONE, PHONE, EMAIL } from 'options/phone/types';
import { HOME as HOME_PHONE, MOBILE_CONTACT, PRIMARY_WORKPLACE } from 'options/phone/uses';
import { IdentificationDemographic, PatientDemographicsEntry } from 'libs/patient/demographics';
import { NameDemographic, initial as initialName } from 'libs/patient/demographics/name';
import { AddressDemographic, initial as initialAddress } from 'libs/patient/demographics/address';
import { PhoneEmailDemographic, initial as initialPhoneEmail } from 'libs/patient/demographics/phoneEmail';
import { RaceDemographic, initial as initialRace } from 'libs/patient/demographics/race';
import {
    BasicAddressEntry,
    BasicIdentificationEntry,
    BasicNewPatientEntry,
    BasicPhoneEmail,
    NameInformationEntry
} from './entry';

const orElseToday = mapOr((value: string) => internalizeDate(value), today);

const asNewExtendedPatientEntry = (initial: BasicNewPatientEntry): PatientDemographicsEntry => {
    const asOf = orElseToday(initial.administrative.asOf);

    const names = asNames(asOf)(initial.name);
    const addresses = asAddresses(asOf)(initial.address);
    const phoneEmails = asPhones(asOf)(initial.phoneEmail);
    const races = asRaces(asOf)(initial.ethnicityRace?.races);
    const identifications = asIdentifications(asOf)(initial.identifications);

    return {
        administrative: initial.administrative,
        names,
        addresses,
        phoneEmails,
        races,
        identifications,
        ethnicity: { asOf: initial.administrative.asOf, ethnicGroup: initial.ethnicityRace?.ethnicity, detailed: [] },
        sexBirth: {
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

const asName =
    (asOf: string) =>
    (entry: NameInformationEntry): NameDemographic | undefined => {
        if (!isEmpty(entry)) {
            return {
                ...initialName(() => asOf),
                type: { ...LEGAL },
                first: orNull(entry.first),
                middle: orNull(entry.middle),
                last: orNull(entry.last),
                suffix: orNull(entry.suffix)
            };
        }
    };

const asNames = (asOf: string) => maybeMapEach(asName(asOf));

const notDefaultCountry = not(isEqual(asSelectable('840')));

const asAddress =
    (asOf: string) =>
    (entry: BasicAddressEntry): AddressDemographic | undefined => {
        if (!isEmpty(entry) && notDefaultCountry(entry.country)) {
            const { state, county, country, address1, address2, city, zipcode, censusTract } = entry;

            return {
                ...initialAddress(() => asOf),
                type: { ...HOUSE },
                use: { ...HOME_ADDRESS },
                county: orNull(county),
                state: orNull(state),
                country: orNull(country),
                address1: orNull(address1),
                address2: orNull(address2),
                city: orNull(city),
                zipcode: orNull(zipcode),
                censusTract: orNull(censusTract)
            };
        }
    };

const asAddresses = (asOf: string) => maybeMapEach(asAddress(asOf));

const asPhones = (asOf: string) => maybeMapEach(asHomePhone(asOf), asWorkPone(asOf), asCellPhone(asOf), asEmail(asOf));

const asHomePhone =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmailDemographic | undefined => {
        const { home } = entry;

        if (home) {
            return {
                ...initialPhoneEmail(() => asOf),
                type: { ...PHONE },
                use: { ...HOME_PHONE },
                phoneNumber: home
            };
        }
    };

const asWorkPone =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmailDemographic | undefined => {
        const { work } = entry;

        if (!isEmpty(work)) {
            return {
                ...initialPhoneEmail(() => asOf),
                type: { ...PHONE },
                use: { ...PRIMARY_WORKPLACE },
                phoneNumber: orNull(work?.phone),
                extension: orNull(work?.extension)
            };
        }
    };

const asCellPhone =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmailDemographic | undefined => {
        const { cell } = entry;

        if (cell) {
            return {
                ...initialPhoneEmail(() => asOf),
                type: { ...CELL_PHONE },
                use: { ...MOBILE_CONTACT },
                phoneNumber: cell
            };
        }
    };

const asEmail =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmailDemographic | undefined => {
        const { email } = entry;

        if (email) {
            return {
                ...initialPhoneEmail(() => asOf),
                type: { ...EMAIL },
                use: { ...HOME_PHONE },
                email
            };
        }
    };

const asRace =
    (asOf: string) =>
    (entry: Selectable): RaceDemographic | undefined => {
        if (exists(entry)) {
            return {
                ...initialRace(() => asOf),
                race: entry
            };
        }
    };

const asRaces = (asOf: string) => maybeMapAll(asRace(asOf));

const asIdentifications = (asOf: string) => maybeMapAll(asIdentification(asOf));

const asIdentification =
    (asOf: string) =>
    (entry: BasicIdentificationEntry): IdentificationDemographic | undefined => {
        const { type, issuer, value } = entry;

        if (exists(type) && value) {
            return {
                asOf,
                type,
                value,
                issuer
            };
        }
    };

export { asNewExtendedPatientEntry };
