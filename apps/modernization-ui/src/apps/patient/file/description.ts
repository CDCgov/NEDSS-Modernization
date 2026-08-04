import { get, maybeJson } from 'libs/api';

import { Patient } from './patient';

const description = (patientId: number): Promise<Patient> =>
    fetch(get(`/nbs/api/patients/${patientId}/file`))
        .then(maybeJson)
        .then((json) => json as Patient);

export { description };
