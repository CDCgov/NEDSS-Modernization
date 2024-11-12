import { Selectable, asSelectable } from 'options';
import { TextCriteria } from 'options/operator';

const ACTIVE = asSelectable('ACTIVE', 'Active');

const statusOptions: Selectable[] = [
    ACTIVE,
    asSelectable('LOG_DEL', 'Deleted'),
    asSelectable('SUPERCEDED', 'Superseded')
];

export { statusOptions };

type BasicInformation = {
    lastName?: TextCriteria;
    firstName?: TextCriteria;
    dateOfBirth?: string;
    gender?: Selectable;
    id?: string;
    status: Selectable[];
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

type EventIds = {
    morbidity?: string;
    document?: string;
    stateCase?: string;
    abcCase?: string;
    cityCountyCase?: string;
    notification?: string;
    labReport?: string;
    accessionNumber?: string;
};

type PatientCriteriaEntry = BasicInformation & Address & Contact & RaceEthnicity & Identification & EventIds;

export type { PatientCriteriaEntry, BasicInformation, Identification, RaceEthnicity, Contact };

const initial: PatientCriteriaEntry = {
    status: [ACTIVE]
};

export { initial };
