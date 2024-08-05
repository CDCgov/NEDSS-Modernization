import { Selectable } from 'options';

type BasicInformation = {
    lastName?: string;
    firstName?: string;
    dateOfBirth?: string;
    gender?: Selectable;
    id?: string;
    status: Selectable[];
    includeSimilar?: boolean;
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
    status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }]
};

export { initial };
