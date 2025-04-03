import { internalizeDate } from 'date';
import {
    BasicAddressEntry,
    BasicNewPatientEntry,
    BasicPersonalDetailsEntry,
    NameInformationEntry,
    BasicPhoneEmail,
    BasicEthnicityRace,
    BasicIdentificationEntry,
    initial
} from './entry';
import { asTextCriteriaValue, TextCriteria } from 'options/operator';
import { resolveDate } from 'design-system/date/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';
import { Selectable } from 'options';

const resolveCriteria = (textCriteria?: TextCriteria): string | undefined =>
    asTextCriteriaValue(textCriteria) ?? undefined;

const asBasicNewPatientEntry = (criteria: Partial<PatientCriteriaEntry>): BasicNewPatientEntry => {
    return {
        administrative: { asOf: internalizeDate(new Date()) },
        name: nameBasic(criteria),
        personalDetails: personalDetailsBasic(criteria),
        address: addressBasic(criteria, initial().address),
        phoneEmail: phoneEmailBasic(criteria),
        ethnicityRace: ethnicityRaceBasic(criteria),
        identifications: identificationBasic(criteria)
    };
};

const nameBasic = (initial: Partial<PatientCriteriaEntry>): NameInformationEntry => ({
    first: resolveCriteria(initial.name?.first),
    last: resolveCriteria(initial.name?.last)
});

const resolveGender = (gender?: Selectable) => (gender?.value === 'NO_VALUE' ? undefined : gender);

const personalDetailsBasic = (initial: Partial<PatientCriteriaEntry>): BasicPersonalDetailsEntry => {
    const bornOn = resolveDate(initial.bornOn);
    const currentSex = resolveGender(initial.gender);

    return {
        bornOn,
        currentSex
    };
};

const addressBasic = (initial: Partial<PatientCriteriaEntry>, defaults?: BasicAddressEntry): BasicAddressEntry => ({
    address1: resolveCriteria(initial.location?.street),
    city: resolveCriteria(initial.location?.city),
    state: initial.state,
    zipcode: initial.zip?.toString(),
    country: defaults?.country
});

const phoneEmailBasic = (initial: Partial<PatientCriteriaEntry>): BasicPhoneEmail => ({
    home: initial.phoneNumber,
    email: initial.email
});

const ethnicityRaceBasic = (initial: Partial<PatientCriteriaEntry>): BasicEthnicityRace => ({
    ethnicity: initial.ethnicity,
    races: initial.race && [initial.race]
});

const identificationBasic = (initial: Partial<PatientCriteriaEntry>): BasicIdentificationEntry[] => {
    return initial.identification && initial.identificationType
        ? [
              {
                  type: initial.identificationType,
                  id: initial.identification
              }
          ]
        : [];
};

export { asBasicNewPatientEntry };
