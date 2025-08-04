import { PatientFileDocument } from 'generated';
import { PatientFileDocument as Document } from './documents';
import { maybeDate } from 'date';

const transformer = (response: PatientFileDocument): Document => ({
    ...response,
    receivedOn: maybeDate(response.receivedOn),
    reportedOn: maybeDate(response.reportedOn)
});

export { transformer };
