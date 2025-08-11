import { maybeJson } from 'libs/api';
import { PatientFileContacts } from 'apps/patient/file/events/contacts/contacts';
import { transformer } from 'apps/patient/file/events/contacts/transformer';
import { mapOr } from 'utils/mapping';

const patientsNamed = (patient: number): Promise<PatientFileContacts[]> =>
    fetch(`/nbs/api/patients/${patient}/contacts/named`, { credentials: 'same-origin' })
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientsNamed };
