import {
    Administrative,
    Name,
    Address,
    PhoneEmail,
    Identification,
    Race,
    Ethnicity,
    Sex,
    Birth,
    Mortality,
    GeneralInformation
} from 'apps/patient/data/api';

type NewPatient = {
    administrative?: Administrative;
    names?: Name[];
    addresses?: Address[];
    phoneEmails?: PhoneEmail[];
    identifications?: Identification[];
    races?: Race[];
    ethnicity?: Ethnicity;
    birth?: Birth;
    gender?: Sex;
    mortality?: Mortality;
    general?: GeneralInformation;
};

type CreatedPatient = {
    id: number;
    shortId: number;
    name?: {
        first?: string;
        last?: string;
    };
};

export type { NewPatient, CreatedPatient };
