export type MatchingCriteria = {
    field: MatchingFieldOption;
    method: MatchingMethodOption;
};

export type MatchingFieldOption = {
    value: MatchingField;
    name: string;
};

export const MATCHING_FIELD_OPTIONS: { [key in MatchingField]: MatchingFieldOption } = {
    firstName: { value: 'firstName', name: 'First name' },
    lastName: { value: 'lastName', name: 'Last name' },
    suffix: { value: 'suffix', name: 'Suffix' },
    birthDate: { value: 'birthDate', name: 'Date of birth' },
    mrn: { value: 'mrn', name: 'MRN' },
    ssn: { value: 'ssn', name: 'SSN' },
    sex: { value: 'sex', name: 'Current sex' },
    gender: { value: 'gender', name: 'Gender' },
    race: { value: 'race', name: 'Race' },
    address: { value: 'address', name: 'Street address' },
    city: { value: 'city', name: 'City' },
    state: { value: 'state', name: 'State' },
    zip: { value: 'zip', name: 'Zip' },
    county: { value: 'county', name: 'County' },
    telephone: { value: 'telephone', name: 'Telephone' }
};

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

export type MatchingMethod = 'jarowinkler';
export type MatchingMethodOption = {
    value: string;
    name: string;
};

export const MATCHING_METHOD_OPTIONS: MatchingMethodOption[] = [
    { value: '', name: '- Select -' },
    { value: 'jarowinkler', name: 'JaroWinkler' }
];
