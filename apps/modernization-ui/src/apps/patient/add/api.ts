import {
    Address,
    Administrative,
    Birth,
    Ethnicity,
    GeneralInformation,
    Identification,
    Mortality,
    Name,
    PhoneEmail,
    Sex,
} from 'apps/patient/data/api';
import { Race } from 'apps/patient/data/race/api';

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

type Transformer<E> = (entry: E) => NewPatient;
type Creator = (input: NewPatient) => Promise<CreatedPatient>;

export type { NewPatient, CreatedPatient, Transformer, Creator };
