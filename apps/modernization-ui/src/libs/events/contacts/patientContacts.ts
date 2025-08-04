import { maybeJson } from 'libs/api';
import { mapOr } from 'utils/mapping';
import { PatientFileContacts } from './contactsNamed';
import { transformer } from './transformer';

const patientContacts = (patient: number): Promise<PatientFileContacts[]> =>
    fetch(`/nbs/api/patients/${patient}/contacts`, { credentials: 'same-origin' })
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientContacts };
