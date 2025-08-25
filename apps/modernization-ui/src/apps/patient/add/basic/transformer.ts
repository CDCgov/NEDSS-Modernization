import { exists, isEmpty, orUndefined } from 'utils';
import { internalizeDate, today } from 'date';
import { mapOr, Mapping, maybeMap, maybeMapAll, maybeMapEach } from 'utils/mapping';
import { asSelectable, asValue, isEqual, Selectable } from 'options';
import { LEGAL } from 'options/name/types';
import { HOME as HOME_ADDRESS } from 'options/address/uses';
import { HOUSE } from 'options/address/types';
import { CELL_PHONE, PHONE, EMAIL } from 'options/phone/types';
import { HOME as HOME_PHONE, MOBILE_CONTACT, PRIMARY_WORKPLACE } from 'options/phone/uses';
import { PatientDemographicsRequest } from 'libs/patient/demographics/request';
import {
    NameDemographicRequest,
    BirthDemographicRequest,
    SexDemographicRequest,
    MortalityDemographicRequest,
    GeneralInformationDemographicRequest,
    AddressDemographicRequest,
    EthnicityDemographicRequest,
    IdentificationDemographicRequest,
    PhoneEmailDemographicRequest,
    RaceDemographicRequest
} from 'libs/patient/demographics';
import { asAdministrative } from 'libs/patient/demographics/administrative';
import {
    BasicAddressEntry,
    BasicEthnicityRace,
    BasicIdentificationEntry,
    BasicNewPatientEntry,
    BasicPhoneEmail,
    NameInformationEntry,
    BasicPersonalDetailsEntry
} from './entry';
import { not } from 'utils/predicate';
import { isAllowed, Sensitive } from 'libs/sensitive';

const orElseToday = mapOr((value: string) => internalizeDate(value), today);

const maybeAsAdministrative = maybeMap(asAdministrative);
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

const transformer = (entry: BasicNewPatientEntry): PatientDemographicsRequest => {
    const asOf = orElseToday(entry.administrative.asOf);

    const administrative = maybeAsAdministrative(entry.administrative);
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
    (entry: NameInformationEntry): NameDemographicRequest | undefined => {
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
    (entry: BasicPersonalDetailsEntry): BirthDemographicRequest | undefined => {
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
    (entry: BasicPersonalDetailsEntry): SexDemographicRequest | undefined => {
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
    (entry: BasicPersonalDetailsEntry): MortalityDemographicRequest | undefined => {
        const { deceased, deceasedOn } = entry;

        if (deceased || deceasedOn) {
            return {
                asOf,
                deceasedOn,
                deceased: asValue(deceased)
            };
        }
    };

const asValueIfAllowed = (sensitive?: Sensitive<string>) => {
    if (isAllowed(sensitive) && sensitive.value) {
        return sensitive.value;
    }
};

const asGeneral =
    (asOf: string) =>
    (entry: BasicPersonalDetailsEntry): GeneralInformationDemographicRequest | undefined => {
        const stateHIVCase = asValueIfAllowed(entry.stateHIVCase);

        if (entry.maritalStatus || stateHIVCase) {
            return {
                asOf,
                stateHIVCase,
                maritalStatus: asValue(entry.maritalStatus)
            };
        }
    };

const notDefaultCountry = not(isEqual(asSelectable('840')));

const asAddress =
    (asOf: string) =>
    (entry: BasicAddressEntry): AddressDemographicRequest | undefined => {
        if (!isEmpty(entry) && notDefaultCountry(entry.country)) {
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
    (entry: BasicEthnicityRace): EthnicityDemographicRequest | undefined => {
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
    (entry: Selectable): RaceDemographicRequest | undefined => {
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
    (entry: BasicPhoneEmail): PhoneEmailDemographicRequest | undefined => {
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
    (entry: BasicPhoneEmail): PhoneEmailDemographicRequest | undefined => {
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
    (entry: BasicPhoneEmail): PhoneEmailDemographicRequest | undefined => {
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
    (entry: BasicPhoneEmail): PhoneEmailDemographicRequest | undefined => {
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
    (entry: BasicIdentificationEntry): IdentificationDemographicRequest | undefined => {
        const { type, issuer, value } = entry;

        if (exists(type) && value) {
            return {
                asOf,
                type: asValue(type),
                value,
                issuer: asValue(issuer)
            };
        }
    };

export { transformer };
