import { PatientDemographicsRequest } from 'libs/patient/demographics/request';

type CreatedPatient = {
    id: number;
    shortId: number;
    name?: {
        first?: string;
        last?: string;
    };
};

type Creator = (input: PatientDemographicsRequest) => Promise<CreatedPatient>;

export type { CreatedPatient, Creator };
