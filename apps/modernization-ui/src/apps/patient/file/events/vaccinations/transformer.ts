import { PatientVaccination } from 'generated';
import { maybeDate } from 'date';
import { PatientFileVaccinations } from '.';

const transformer = (response: PatientVaccination): PatientFileVaccinations => ({
    ...response,
    createdOn: maybeDate(response.createdOn),
    administeredOn: maybeDate(response.administeredOn)
});

export { transformer };
