import { PatientMorbidityReport } from 'generated';
import { PatientFileMorbidityReport } from './morbidity-report';
import { maybeDate } from 'date';

const transformer = (response: PatientMorbidityReport): PatientFileMorbidityReport => ({
    ...response,
    addedOn: new Date(response.addedOn),
    receivedOn: maybeDate(response.receivedOn),
    reportedOn: maybeDate(response.reportedOn)
});

export { transformer };
