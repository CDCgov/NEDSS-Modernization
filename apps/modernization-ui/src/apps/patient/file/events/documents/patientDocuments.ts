import { maybeJson } from 'libs/api';
import { PatientFileDocument } from './documents';
import { mapOr } from 'utils/mapping';
import { transformer } from './transformer';

const patientDocuments = (patient: number): Promise<PatientFileDocument[]> =>
    fetch(`/nbs/api/patients/${patient}/documents`, { credentials: 'same-origin' })
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientDocuments };
