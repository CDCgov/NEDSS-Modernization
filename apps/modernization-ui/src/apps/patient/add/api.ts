import {
    Administrative,
    Name,
    Address,
    PhoneEmail,
    Identification,
    Ethnicity,
    Sex,
    Birth,
    Mortality,
    GeneralInformation
} from 'apps/patient/data/api';
import { Race } from 'apps/patient/data/race/api';
import { PatientDemographicsRequest } from 'libs/patient/demographics/request';

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

type Transformer<E> = (entry: E) => PatientDemographicsRequest;
type Creator = (input: PatientDemographicsRequest) => Promise<CreatedPatient>;

export type { NewPatient, CreatedPatient, Transformer, Creator };
