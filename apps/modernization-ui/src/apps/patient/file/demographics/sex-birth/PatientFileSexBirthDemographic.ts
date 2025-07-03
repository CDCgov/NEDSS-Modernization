import { AgeResolver } from 'date';
import { SexBirthDemographic } from 'libs/patient/demographics/sex-birth';

type PatientFileSexBirthDemographic = {
    demographic: SexBirthDemographic;
    ageResolver: AgeResolver;
};

export type { PatientFileSexBirthDemographic };
