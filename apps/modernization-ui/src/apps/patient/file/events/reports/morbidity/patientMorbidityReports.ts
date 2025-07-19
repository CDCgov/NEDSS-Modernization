import { maybeJson } from 'libs/api';
import { mapOr } from 'utils/mapping';
import { PatientFileMorbidityReport } from './morbidity-report';
import { transformer } from './transformer';

const patientMorbidityReports = (patient: number): Promise<PatientFileMorbidityReport[]> =>
    fetch(`/nbs/api/patients/${patient}/reports/morbidity`, { credentials: 'same-origin' })
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientMorbidityReports };
