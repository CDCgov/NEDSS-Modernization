import { maybeJson } from 'libs/api';
import { mapOr } from 'utils/mapping';
import { transformer } from './transformer';
import { PatientFileVaccinations } from '.';

const patientVaccinations = (patient: number): Promise<PatientFileVaccinations[]> =>
    fetch(`/nbs/api/patients/${patient}/vaccinations`, { credentials: 'same-origin' })
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientVaccinations };
