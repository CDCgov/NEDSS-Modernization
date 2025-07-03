import { Selectable, findByValue } from 'options';

const FEMALE = { name: 'Female', value: 'F' };
const MALE = { name: 'Male', value: 'M' };
const UNKNOWN = { name: 'Unknown', value: 'U' };

type Genders = {
    female: Selectable;
    male: Selectable;
    unknown: Selectable;
    all: Selectable[];
};

const genders: Genders = {
    female: FEMALE,
    male: MALE,
    unknown: UNKNOWN,
    all: [FEMALE, MALE, UNKNOWN]
};

const asSelectableGender = (value: string | null | undefined) =>
    (value && findByValue(genders.all, UNKNOWN)(value)) || UNKNOWN;

export { genders, asSelectableGender, UNKNOWN };
export type { Genders };
