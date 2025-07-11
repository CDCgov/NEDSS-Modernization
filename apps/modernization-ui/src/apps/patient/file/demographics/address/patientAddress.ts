import { PatientFileService } from 'generated';
import { AddressDemographic } from 'libs/patient/demographics/address';

const patientAddress = (patient: number): Promise<AddressDemographic[]> =>
    PatientFileService.addresses({ patient }).then();

export { patientAddress };
