import { PatientFileService, PatientInvestigation as PatientInvestigationResponse } from 'generated';
import { maybeDate } from 'date';
import { mapOr } from 'utils/mapping';
import { PatientFileInvestigation } from './investigation';

const transformer = (value: PatientInvestigationResponse): PatientFileInvestigation => ({
    ...value,
    startedOn: maybeDate(value.startedOn)
});

const patientInvestigations = (patient: number) =>
    PatientFileService.investigations({ patientId: patient })
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientInvestigations };
