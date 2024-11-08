export type BlockingCriteria = {
    field: BlockingFieldOption;
    method?: BlockingMethodOption;
};

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

export type BlockingFieldOption = {
    value: BlockingField;
    name: string;
};

export const BLOCKING_FIELD_OPTIONS: { [key in BlockingField]: BlockingFieldOption } = {
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

export type BlockingMethod = 'exact' | 'firstFour' | 'lastFour';

export type BlockingMethodOption = {
    value: BlockingMethod;
    name: string;
};
export const BLOCKING_METHOD_OPTIONS: BlockingMethodOption[] = [
    { value: 'exact', name: 'Exact' },
    { value: 'firstFour', name: 'First four characters' },
    { value: 'lastFour', name: 'Last four characters' }
];
