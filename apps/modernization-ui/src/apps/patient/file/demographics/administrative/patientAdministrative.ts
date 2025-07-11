import { PatientFileService } from 'generated';

const patientAdministrative = (patient: number) => PatientFileService.administrative({ patient }).then();

export { patientAdministrative };
