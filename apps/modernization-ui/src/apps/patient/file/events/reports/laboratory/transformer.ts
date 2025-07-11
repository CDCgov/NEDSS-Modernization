import { PatientLabReport as PatientLabReportResponse } from 'generated';
import { PatientFileLaboratoryReport } from './laboratory-report';
import { maybeDate } from 'date';

const transformer = (response: PatientLabReportResponse): PatientFileLaboratoryReport => ({
    ...response,
    receivedDate: maybeDate(response.receivedDate),
    collectedDate: maybeDate(response.collectedDate)
});

export { transformer };
