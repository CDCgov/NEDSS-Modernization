import { PatientFileService } from 'generated';
import { transformer } from './transformer';

const patientLaboratoryReports = (patient: number) =>
    PatientFileService.laboratoryReports({ patient }).then((response) => (response ? response.map(transformer) : []));

export { patientLaboratoryReports };
