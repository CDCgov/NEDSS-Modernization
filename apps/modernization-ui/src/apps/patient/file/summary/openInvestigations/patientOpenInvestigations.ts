import { PatientFileService, PatientInvestigation as PatientInvestigationResponse } from 'generated';
import { maybeDate } from 'date';
import { PatientFileOpenInvestigation } from './openInvestigation';

const transformer = (value: PatientInvestigationResponse): PatientFileOpenInvestigation => ({
    ...value,
    startedOn: maybeDate(value.startedOn)
});

const patientOpenInvestigations = (patient: number) =>
    PatientFileService.openInvestigations({ patientId: patient })
        .then((response) => (response ? response.map(transformer) : []))
        .catch(() => []);

export { patientOpenInvestigations };
