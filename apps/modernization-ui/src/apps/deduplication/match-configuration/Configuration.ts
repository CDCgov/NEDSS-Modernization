export type MatchingConfiguration = {
    passes: Pass[];
};

export type Pass = {
    name: string;
    description: string;
    active: boolean;
    blockingCriteria: BlockingCriteria[];
    matchingCriteria: MatchingCriteria[];
    lowerBound: number;
    upperBound: number;
};

export type BlockingCriteria = {
    field: BlockingField;
    method: BlockingMethod;
};

export type BlockingFieldOption = { value: BlockingField; label: string };

export const blockingFieldOptions: BlockingFieldOption[] = [
    { value: 'firstName', label: 'First name' },
    { value: 'lastName', label: 'Last name' },
    { value: 'suffix', label: 'Suffix' },
    { value: 'birthDate', label: 'Date of birth' },
    { value: 'mrn', label: 'MRN' },
    { value: 'ssn', label: 'SSN' },
    { value: 'sex', label: 'Current sex' },
    { value: 'gender', label: 'Gender' },
    { value: 'race', label: 'Race' },
    { value: 'address', label: 'Street address' },
    { value: 'city', label: 'City' },
    { value: 'state', label: 'State' },
    { value: 'zip', label: 'Zip' },
    { value: 'county', label: 'County' },
    { value: 'telephone', label: 'Telephone' }
];

export type BlockingField =
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

export type BlockingMethod = 'exact' | 'firstFour' | 'lastFour';

export type BlockingMethodOption = {
    value: BlockingMethod;
    label: string;
};
export const blockingMethodOptions: BlockingMethodOption[] = [
    { value: 'exact', label: 'Exact' },
    { value: 'firstFour', label: 'First four characters' },
    { value: 'lastFour', label: 'Last four characters' }
];

export type MatchingCriteria = {
    field: MatchingField;
    method: MatchingMethod;
};

export type MatchingFieldOption = { value: MatchingField; label: string };
const matchingFieldOptions: BlockingFieldOption[] = [
    { value: 'firstName', label: 'First name' },
    { value: 'lastName', label: 'Last name' },
    { value: 'suffix', label: 'Suffix' },
    { value: 'birthDate', label: 'Date of birth' },
    { value: 'mrn', label: 'MRN' },
    { value: 'ssn', label: 'SSN' },
    { value: 'sex', label: 'Current sex' },
    { value: 'gender', label: 'Gender' },
    { value: 'race', label: 'Race' },
    { value: 'address', label: 'Street address' },
    { value: 'city', label: 'City' },
    { value: 'state', label: 'State' },
    { value: 'zip', label: 'Zip' },
    { value: 'county', label: 'County' },
    { value: 'telephone', label: 'Telephone' }
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
    label: string;
};
export const matchingMethodOptions: MatchingMethodOption[] = [
    { value: 'exact', label: 'Exact' },
    { value: 'logOdds', label: 'Log odds' }
];
