import { Selectable, asSelectable } from 'options';

const genderOptions: Selectable[] = [
    asSelectable('F', 'Female'),
    asSelectable('M', 'Male'),
    asSelectable('U', 'Other')
];

const ACTIVE = asSelectable('ACTIVE', 'Active');

const statusOptions: Selectable[] = [
    ACTIVE,
    asSelectable('LOG_DEL', 'Deleted'),
    asSelectable('SUPERCEDED', 'Superseded')
];

export { genderOptions, statusOptions };

type BasicInformation = {
    lastName?: string;
    firstName?: string;
    dateOfBirth?: string;
    gender?: Selectable;
    id?: string;
    status: Selectable[];
    disableSoundex?: boolean;
};

type Address = {
    address?: string;
    city?: string;
    state?: Selectable;
    zip?: number;
};

type Contact = {
    phoneNumber?: string;
    email?: string;
};

type RaceEthnicity = {
    race?: Selectable;
    ethnicity?: Selectable;
};

type Identification = {
    identification?: string;
    identificationType?: Selectable;
};

type PatientCriteriaEntry = BasicInformation & Address & Contact & RaceEthnicity & Identification;

export type { PatientCriteriaEntry, Identification, RaceEthnicity, Contact };

const initial: PatientCriteriaEntry = {
    status: [ACTIVE]
};

export { initial };
