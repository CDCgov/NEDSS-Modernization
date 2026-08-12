import { maybeDate } from 'date';
import { PatientFileDocument } from 'generated';

import { PatientFileDocument as Document } from './documents';

const transformer = (response: PatientFileDocument): Document => ({
    ...response,
    receivedOn: maybeDate(response.receivedOn),
    reportedOn: maybeDate(response.reportedOn),
});

export { transformer };
