import { get, maybeJson } from 'libs/api';
import { mapOr } from 'utils/mapping';
import { PatientFileContacts, transformer } from '../contacts';

const patientContacts = (patient: number): Promise<PatientFileContacts[]> =>
    fetch(get(`/nbs/api/patients/${patient}/contacts`))
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientContacts };
