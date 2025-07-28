import { PatientFileService } from 'generated';
import { mapOr } from 'utils/mapping';
import { transformer } from './transformer';

const patientTreatments = (patient: number) =>
    PatientFileService.treatments({ patient })
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientTreatments };
