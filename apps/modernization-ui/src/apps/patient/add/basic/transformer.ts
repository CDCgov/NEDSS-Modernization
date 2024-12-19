import { exists, isEmpty, orUndefined } from 'utils';
import { Mapping, maybeMap, maybeMapAll } from 'utils/mapping';
import { asValue, Selectable } from 'options';
import { LEGAL } from 'options/name/types';
import { HOME as HOME_ADDRESS } from 'options/address/uses';
import { HOUSE } from 'options/address/types';
import { CELL_PHONE, PHONE, EMAIL } from 'options/phone/types';
import { HOME as HOME_PHONE, MOBILE_CONTACT, PRIMARY_WORKPLACE } from 'options/phone/uses';
import { Race } from 'apps/patient/data/race/api';
import { NewPatient } from 'apps/patient/add/api';
import {
    Address,
    Birth,
    Ethnicity,
    GeneralInformation,
    Identification,
    Mortality,
    Name,
    PhoneEmail,
    Sex
} from 'apps/patient/data/api';

import { asAdministrative } from 'apps/patient/data';

import {
    BasicAddressEntry,
    BasicEthnicityRace,
    BasicIdentificationEntry,
    BasicNewPatientEntry,
    BasicPhoneEmail,
    NameInformationEntry,
    BasicPersonalDetailsEntry
} from './entry';

const maybeMapEach =
    <R, S>(...mappings: Mapping<R, S>[]) =>
    (value?: R): NonNullable<S>[] => {
        if (value) {
            return mappings.reduce((previous, current) => {
                const mapped = current(value);
                return mapped ? [...previous, mapped] : previous;
            }, [] as NonNullable<S>[]);
        }

        return [];
    };

const maybeAsBirth = (asOf: string) => maybeMap(asBirth(asOf));
const maybeAsGender = (asOf: string) => maybeMap(asGender(asOf));
const maybeAsMortality = (asOf: string) => maybeMap(asMortality(asOf));
const maybeAsGeneral = (asOf: string) => maybeMap(asGeneral(asOf));
const maybeAsEthnicity = (asOf: string) => maybeMap(asEthnicity(asOf));

const asNames = (asOf: string) => maybeMapEach(asName(asOf));
const asAddresses = (asOf: string) => maybeMapEach(asAddress(asOf));
const asRaces = (asOf: string) => maybeMapAll(asRace(asOf));
const asPhones = (asOf: string) => maybeMapEach(asHomePhone(asOf), asWorkPone(asOf), asCellPhone(asOf), asEmail(asOf));

const asIdentifications = (asOf: string) => maybeMapAll(asIdentification(asOf));

const transformer = (entry: BasicNewPatientEntry): NewPatient => {
    const administrative = asAdministrative(entry.administrative);

    const { asOf } = administrative;

    const names = asNames(asOf)(entry.name);
    const birth = maybeAsBirth(asOf)(entry.personalDetails);
    const gender = maybeAsGender(asOf)(entry.personalDetails);
    const mortality = maybeAsMortality(asOf)(entry.personalDetails);
    const general = maybeAsGeneral(asOf)(entry.personalDetails);
    const addresses = asAddresses(asOf)(entry.address);
    const ethnicity = maybeAsEthnicity(asOf)(entry.ethnicityRace);
    const races = asRaces(asOf)(entry.ethnicityRace?.races);
    const phoneEmails = asPhones(asOf)(entry.phoneEmail);
    const identifications = asIdentifications(asOf)(entry.identifications);

    return {
        administrative,
        names,
        birth,
        gender,
        mortality,
        general,
        addresses,
        ethnicity,
        races,
        phoneEmails,
        identifications
    };
};

const asName =
    (asOf: string) =>
    (entry: NameInformationEntry): Name | undefined => {
        if (!isEmpty(entry)) {
            const { first, middle, last, suffix } = entry;
            return {
                asOf,
                type: LEGAL.value,
                first: orUndefined(first),
                middle: orUndefined(middle),
                last: orUndefined(last),
                suffix: asValue(suffix)
            };
        }
    };

const asBirth =
    (asOf: string) =>
    (entry: BasicPersonalDetailsEntry): Birth | undefined => {
        const { bornOn, birthSex } = entry;

        if (bornOn || birthSex) {
            return {
                asOf,
                bornOn,
                sex: asValue(birthSex)
            };
        }
    };

const asGender =
    (asOf: string) =>
    (entry: BasicPersonalDetailsEntry): Sex | undefined => {
        const { currentSex } = entry;

        if (currentSex) {
            return {
                asOf,
                current: asValue(currentSex)
            };
        }
    };

const asMortality =
    (asOf: string) =>
    (entry: BasicPersonalDetailsEntry): Mortality | undefined => {
        const { deceased, deceasedOn } = entry;

        if (deceased || deceasedOn) {
            return {
                asOf,
                deceasedOn,
                deceased: asValue(deceased)
            };
        }
    };

const asGeneral =
    (asOf: string) =>
    (entry: BasicPersonalDetailsEntry): GeneralInformation | undefined => {
        const { maritalStatus, stateHIVCase } = entry;
        if (maritalStatus || stateHIVCase) {
            return {
                asOf,
                stateHIVCase,
                maritalStatus: asValue(maritalStatus)
            };
        }
    };

const asAddress =
    (asOf: string) =>
    (entry: BasicAddressEntry): Address | undefined => {
        if (!isEmpty(entry)) {
            const { state, county, country, address1, address2, city, zipcode, censusTract } = entry;

            return {
                asOf,
                type: HOUSE.value,
                use: HOME_ADDRESS.value,
                county: asValue(county),
                state: asValue(state),
                country: asValue(country),
                address1: orUndefined(address1),
                address2: orUndefined(address2),
                city: orUndefined(city),
                zipcode: orUndefined(zipcode),
                censusTract: orUndefined(censusTract)
            };
        }
    };

const asEthnicity =
    (asOf: string) =>
    (entry: BasicEthnicityRace): Ethnicity | undefined => {
        const { ethnicity } = entry;

        if (exists(ethnicity)) {
            return {
                asOf,
                ethnicGroup: asValue(ethnicity),
                detailed: []
            };
        }
    };

const asRace =
    (asOf: string) =>
    (entry: Selectable): Race | undefined => {
        if (exists(entry)) {
            return {
                asOf,
                race: asValue(entry),
                detailed: []
            };
        }
    };

const asHomePhone =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmail | undefined => {
        const { home } = entry;

        if (home) {
            return {
                asOf,
                type: PHONE.value,
                use: HOME_PHONE.value,
                phoneNumber: home
            };
        }
    };

const asWorkPone =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmail | undefined => {
        const { work } = entry;

        if (!isEmpty(entry.work)) {
            return {
                asOf,
                type: PHONE.value,
                use: PRIMARY_WORKPLACE.value,
                phoneNumber: work?.phone,
                extension: work?.extension
            };
        }
    };

const asCellPhone =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmail | undefined => {
        const { cell } = entry;

        if (cell) {
            return {
                asOf,
                type: CELL_PHONE.value,
                use: MOBILE_CONTACT.value,
                phoneNumber: cell
            };
        }
    };

const asEmail =
    (asOf: string) =>
    (entry: BasicPhoneEmail): PhoneEmail | undefined => {
        const { email } = entry;

        if (email) {
            return {
                asOf,
                type: EMAIL.value,
                use: HOME_PHONE.value,
                email
            };
        }
    };

const asIdentification =
    (asOf: string) =>
    (entry: BasicIdentificationEntry): Identification | undefined => {
        const { type, issuer, id } = entry;

        if (exists(type) && id) {
            return {
                asOf,
                type: asValue(type),
                id,
                issuer: asValue(issuer)
            };
        }
    };

export { transformer };
