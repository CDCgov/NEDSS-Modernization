import { DateCriteria } from 'design-system/date/criteria';
import { Selectable, asSelectable } from 'options';
import { TextCriteria, AlphaTextCriteria } from 'options/operator';

const ACTIVE = asSelectable('ACTIVE', 'Active');

const statusOptions: Selectable[] = [
    ACTIVE,
    asSelectable('LOG_DEL', 'Deleted'),
    asSelectable('SUPERCEDED', 'Superseded')
];

export { statusOptions };

type NameCriteria = {
    first?: TextCriteria;
    last?: TextCriteria;
};

type LocationCriteria = {
    street?: AlphaTextCriteria;
    city?: AlphaTextCriteria;
};

type BasicInformation = {
    name?: NameCriteria;
    dateOfBirth?: string;
    gender?: Selectable;
    id?: string;
    status: Selectable[];
    bornOn?: DateCriteria;
};

type Address = {
    location?: LocationCriteria;
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
    investigation?: string;
    treatment?: string;
    vaccination?: string;
};

type Filter = {
    filter?: {
        patientid?: string;
    };
};

type PatientCriteriaEntry = BasicInformation & Address & Contact & RaceEthnicity & Identification & EventIds & Filter;

export type { PatientCriteriaEntry, BasicInformation, Identification, RaceEthnicity, Contact, NameCriteria, Filter };

const initial: PatientCriteriaEntry = {
    status: [ACTIVE]
};

export { initial };
