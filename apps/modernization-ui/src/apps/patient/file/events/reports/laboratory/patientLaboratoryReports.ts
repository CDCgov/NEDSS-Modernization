import { PatientFileService } from 'generated';
import { mapOr } from 'utils/mapping';
import { transformer } from './transformer';

const patientLaboratoryReports = (patient: number) =>
    PatientFileService.laboratoryReports({ patient })
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientLaboratoryReports };
