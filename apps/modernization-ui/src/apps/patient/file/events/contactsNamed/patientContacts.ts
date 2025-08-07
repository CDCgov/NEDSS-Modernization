import { maybeJson } from 'libs/api';
import { PatientFileContacts } from 'libs/events/contacts/contactsNamed';
import { transformer } from 'libs/events/contacts/transformer';
import { mapOr } from 'utils/mapping';

const patientContacts = (patient: number): Promise<PatientFileContacts[]> =>
    fetch(`/nbs/api/patients/${patient}/contacts`, { credentials: 'same-origin' })
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientContacts };
