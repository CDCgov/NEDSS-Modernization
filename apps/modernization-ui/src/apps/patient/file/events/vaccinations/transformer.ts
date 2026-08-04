import { maybeDate } from 'date';
import { PatientVaccination } from 'generated';

import { PatientFileVaccinations } from '.';

const transformer = (response: PatientVaccination): PatientFileVaccinations => ({
    ...response,
    createdOn: maybeDate(response.createdOn),
    administeredOn: maybeDate(response.administeredOn),
});

export { transformer };
