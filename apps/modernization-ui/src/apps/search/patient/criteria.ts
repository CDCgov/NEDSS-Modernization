import { Selectable, asSelectable } from 'options';

const ACTIVE = asSelectable('ACTIVE', 'Active');

const statusOptions: Selectable[] = [
    ACTIVE,
    asSelectable('LOG_DEL', 'Deleted'),
    asSelectable('SUPERCEDED', 'Superseded')
];

export { statusOptions };

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

type EventIds = {
    morbidityId?: string;
    documentId?: string;
    stateCaseId?: string;
    abcCaseId?: string;
    cityCountyCaseId?: string;
    notificationId?: string;
    labReportId?: string;
    accessionNumberId?: string;
};

type PatientCriteriaEntry = BasicInformation & Address & Contact & RaceEthnicity & Identification & EventIds;

export type { PatientCriteriaEntry, BasicInformation, Identification, RaceEthnicity, Contact };

const initial: PatientCriteriaEntry = {
    status: [ACTIVE]
};

export { initial };
