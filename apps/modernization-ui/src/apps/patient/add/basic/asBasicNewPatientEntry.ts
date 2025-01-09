import { asSelectable } from 'options';
import {
    BasicAddressEntry,
    BasicNewPatientEntry,
    BasicPersonalDetailsEntry,
    NameInformationEntry,
    BasicPhoneEmail,
    BasicEthnicityRace,
    BasicIdentificationEntry
} from './entry';
import { Mapping } from 'utils';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';
import { asTextCriteriaValue, TextCriteria } from 'options/operator';
import { internalizeDate } from 'date';
import { isDateEqualsCriteria } from 'design-system/date/entry';

const mapOr =
    <R, S, O>(mapping: Mapping<R, S>, fallback: O) =>
    (value?: R | null): S | O =>
        value ? mapping(value) : fallback;

const asSelectableIfPresent = mapOr(asSelectable, undefined);

const resolveCriteria = (textCriteria?: TextCriteria): string | undefined =>
    asTextCriteriaValue(textCriteria) ?? undefined;

const asBasicNewPatientEntry = (initial: Partial<PatientCriteriaEntry>): BasicNewPatientEntry => {
    return {
        administrative: { asOf: internalizeDate(new Date()) },
        name: nameBasic(initial),
        personalDetails: personalDetailsBasic(initial),
        address: addressBasic(initial),
        phoneEmail: phoneEmailBasic(initial),
        ethnicityRace: ethnicityRaceBasic(initial),
        identifications: identificationBasic(initial)
    };
};

const nameBasic = (initial: Partial<PatientCriteriaEntry>): NameInformationEntry => ({
    first: resolveCriteria(initial.name?.first),
    last: resolveCriteria(initial.name?.last)
});

const personalDetailsBasic = (initial: Partial<PatientCriteriaEntry>): BasicPersonalDetailsEntry => {
    const bornOn =
        initial.bornOn &&
        isDateEqualsCriteria(initial.bornOn) &&
        initial.bornOn.equals.year &&
        initial.bornOn.equals.month &&
        initial.bornOn.equals.day
            ? internalizeDate(
                  new Date(initial.bornOn.equals.year, initial.bornOn.equals.month - 1, initial.bornOn.equals.day)
              )
            : undefined;

    return {
        bornOn,
        currentSex: asSelectableIfPresent(initial.gender?.value)
    };
};

const addressBasic = (initial: Partial<PatientCriteriaEntry>): BasicAddressEntry => ({
    address1: resolveCriteria(initial.location?.street),
    city: resolveCriteria(initial.location?.city),
    state: asSelectableIfPresent(initial.state?.value),
    zipcode: initial.zip?.toString()
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
