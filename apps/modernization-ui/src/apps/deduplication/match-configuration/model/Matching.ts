export type MatchingCriteria = {
    field: MatchingField;
    method: MatchingMethodOption;
};

export type MatchingFieldOption = { active: boolean; value: MatchingField; label: string };
export const matchingFieldOptions: MatchingFieldOption[] = [
    { active: false, value: 'firstName', label: 'First name' },
    { active: false, value: 'lastName', label: 'Last name' },
    { active: false, value: 'suffix', label: 'Suffix' },
    { active: false, value: 'birthDate', label: 'Date of birth' },
    { active: false, value: 'mrn', label: 'MRN' },
    { active: false, value: 'ssn', label: 'SSN' },
    { active: false, value: 'sex', label: 'Current sex' },
    { active: false, value: 'gender', label: 'Gender' },
    { active: false, value: 'race', label: 'Race' },
    { active: false, value: 'address', label: 'Street address' },
    { active: false, value: 'city', label: 'City' },
    { active: false, value: 'state', label: 'State' },
    { active: false, value: 'zip', label: 'Zip' },
    { active: false, value: 'county', label: 'County' },
    { active: false, value: 'telephone', label: 'Telephone' }
];

export type MatchingField =
    | 'firstName'
    | 'lastName'
    | 'suffix'
    | 'birthDate'
    | 'mrn'
    | 'ssn'
    | 'sex'
    | 'gender'
    | 'race'
    | 'address'
    | 'city'
    | 'state'
    | 'zip'
    | 'county'
    | 'telephone';

export type MatchingMethod = 'exact' | 'logOdds';
export type MatchingMethodOption = {
    value: MatchingMethod;
    name: string;
};
export const matchingMethodOptions: MatchingMethodOption[] = [
    { value: 'exact', name: 'Exact' },
    { value: 'logOdds', name: 'Log odds' }
];
