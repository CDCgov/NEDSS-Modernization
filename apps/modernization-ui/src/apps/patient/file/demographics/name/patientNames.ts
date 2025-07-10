import { PatientFileService } from 'generated';
import { NameDemographic } from 'libs/patient/demographics';

const patientNames = (patient: number): Promise<NameDemographic[]> => PatientFileService.names({ patient }).then();

export { patientNames };
