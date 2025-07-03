import { PatientFileService } from 'generated';
import { PhoneEmailDemographic } from 'libs/patient/demographics/phoneEmail/phoneEmails';

const patientPhoneEmail = (patient: number): Promise<Array<PhoneEmailDemographic>> =>
    PatientFileService.phones({ patient }).then();

export { patientPhoneEmail };
