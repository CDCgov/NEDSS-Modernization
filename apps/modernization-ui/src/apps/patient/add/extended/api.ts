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
import { Maybe } from 'utils';

type NewPatient = {
    administrative: Administrative;
    names?: Name[];
    addresses?: Address[];
    phoneEmails?: PhoneEmail[];
    identifications?: Identification[];
    races?: Race[];
    ethnicity?: Maybe<Ethnicity>;
    birth?: Maybe<Birth>;
    sex?: Maybe<Sex>;
    mortality?: Maybe<Mortality>;
    general?: Maybe<GeneralInformation>;
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
