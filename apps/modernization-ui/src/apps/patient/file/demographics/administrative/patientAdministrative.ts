import { get, maybeJson } from 'libs/api';

const patientAdministrative = (patient: number) =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/administrative`)).then(maybeJson);

export { patientAdministrative };
