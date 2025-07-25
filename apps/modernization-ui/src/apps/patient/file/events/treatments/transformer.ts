import { maybeDate } from 'date';
import { PatientFileTreatment as Treatment } from './treatment';
import { PatientFileTreatment } from 'generated';

const transformer = (response: PatientFileTreatment): Treatment => ({
    ...response,
    treatedOn: maybeDate(response.treatedOn),
    createdOn: maybeDate(response.createdOn)
});

export { transformer };
