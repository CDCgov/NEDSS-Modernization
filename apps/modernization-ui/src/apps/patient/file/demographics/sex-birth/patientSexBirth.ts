import { PatientFileService } from 'generated';
import { asOfAgeResolver } from 'date';
import { PatientFileSexBirthDemographic } from './PatientFileSexBirthDemographic';

const patientSexBirth = (patient: number): Promise<PatientFileSexBirthDemographic> =>
    PatientFileService.sexBirthDemographics({ patient }).then((response) => ({
        demographic: response,
        ageResolver: asOfAgeResolver(response?.deceasedOn)
    }));
export { patientSexBirth };
