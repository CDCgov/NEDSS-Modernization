import { PatientFileService } from 'generated';
import { IdentificationDemographic } from 'libs/patient/demographics';

const patientIdentifications = (patient: number): Promise<IdentificationDemographic[]> =>
    PatientFileService.identifications({ patient }).then();

export { patientIdentifications };
