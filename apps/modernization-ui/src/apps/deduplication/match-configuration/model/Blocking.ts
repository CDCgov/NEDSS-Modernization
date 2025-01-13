export type BlockingCriteria = {
    field: BlockingFieldOption;
    method?: BlockingMethodOption;
};

export type BlockingField = 'firstName' | 'lastName' | 'birthDate' | 'mrn' | 'sex' | 'address' | 'zip';

export type BlockingFieldOption = {
    value: BlockingField;
    name: string;
};

export const BLOCKING_FIELD_OPTIONS: { [key in BlockingField]: BlockingFieldOption } = {
    firstName: { value: 'firstName', name: 'First name' },
    lastName: { value: 'lastName', name: 'Last name' },
    birthDate: { value: 'birthDate', name: 'Date of birth' },
    mrn: { value: 'mrn', name: 'MRN' },
    sex: { value: 'sex', name: 'Current sex' },
    address: { value: 'address', name: 'Street address' },
    zip: { value: 'zip', name: 'Zip' }
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
