import { maybeJson } from 'libs/api';
import { PatientFileVaccinations } from './vaccinations';
import { mapOr } from 'utils/mapping';

const patientVaccinations = (patient: number): Promise<PatientFileVaccinations[]> =>
    fetch(`/nbs/api/patients/${patient}/vaccination`, { credentials: 'same-origin' })
        .then(maybeJson)
        .then(mapOr((response) => response.map(), []))
        .catch(() => []);

export { patientVaccinations };
