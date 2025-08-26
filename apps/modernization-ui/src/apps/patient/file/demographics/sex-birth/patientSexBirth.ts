import { get, maybeJson } from 'libs/api';
import { asOfAgeResolver } from 'date';
import { PatientFileSexBirthDemographic } from './PatientFileSexBirthDemographic';

const patientSexBirth = (patient: number): Promise<PatientFileSexBirthDemographic> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/mortality`))
        .then(maybeJson)
        .then((response) => ({
            demographic: response,
            ageResolver: asOfAgeResolver(response?.deceasedOn)
        }));

export { patientSexBirth };
