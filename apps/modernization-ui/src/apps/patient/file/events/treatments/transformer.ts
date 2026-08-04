import { maybeDate } from 'date';
import { PatientFileTreatment } from 'generated';

import { PatientFileTreatment as Treatment } from '.';

const transformer = (response: PatientFileTreatment): Treatment => ({
    ...response,
    treatedOn: maybeDate(response.treatedOn),
    createdOn: maybeDate(response.createdOn),
});

export { transformer };
