import { PatientLabReport as PatientLabReportResponse } from 'generated';
import { PatientLabReport } from './patientLabReport';
import { maybeDate } from 'date';

const transformer = (response: PatientLabReportResponse): PatientLabReport => ({
    ...response,
    receivedDate: maybeDate(response.receivedDate),
    collectedDate: maybeDate(response.collectedDate)
});

export { transformer };
