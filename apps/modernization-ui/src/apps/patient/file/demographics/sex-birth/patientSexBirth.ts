import { PatientFileService } from 'generated';
import { asOfAgeResolver } from 'date';
import { PatientFileSexBirthDemographic } from './PatientFileSexBirthDemographic';
import { SexBirthDemographic } from 'libs/patient/demographics/sex-birth';

const patientSexBirth = (patient: number): Promise<PatientFileSexBirthDemographic> =>
    PatientFileService.sexBirthDemographics({ patient }).then((response) => ({
        demographic: response as SexBirthDemographic,
        ageResolver: asOfAgeResolver(response.deceasedOn)
    }));
export { patientSexBirth };
