import { asSelectable } from 'options';
import { internalizeDate } from 'date';
import { Mapping } from 'utils';
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

const mapOr =
    <R, S, O>(mapping: Mapping<R, S>, fallback: O) =>
    (value?: R | null): S | O =>
        value ? mapping(value) : fallback;

const asSelectableIfPresent = mapOr(asSelectable, undefined);

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

const personalDetailsBasic = (initial: Partial<PatientCriteriaEntry>): BasicPersonalDetailsEntry => {
    const bornOn = resolveDate(initial.bornOn);

    return {
        bornOn,
        currentSex: asSelectableIfPresent(initial.gender?.value)
    };
};

const addressBasic = (initial: Partial<PatientCriteriaEntry>, defaults?: BasicAddressEntry): BasicAddressEntry => ({
    address1: resolveCriteria(initial.location?.street),
    city: resolveCriteria(initial.location?.city),
    state: asSelectableIfPresent(initial.state?.value),
    zipcode: initial.zip?.toString(),
    country: defaults?.country
});

const phoneEmailBasic = (initial: Partial<PatientCriteriaEntry>): BasicPhoneEmail => ({
    home: initial.phoneNumber,
    email: initial.email
});

const ethnicityRaceBasic = (initial: Partial<PatientCriteriaEntry>): BasicEthnicityRace => ({
    ethnicity: asSelectableIfPresent(initial.ethnicity?.value),
    races: initial.race && [initial.race]
});

const identificationBasic = (initial: Partial<PatientCriteriaEntry>): BasicIdentificationEntry[] => {
    const identifications: BasicIdentificationEntry[] = [];
    if (initial.identification && initial.identificationType) {
        identifications.push({
            type: asSelectable(initial.identificationType.value),
            id: initial.identification
        });
    }
    return identifications;
};

export { asBasicNewPatientEntry };
