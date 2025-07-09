import { PatientFileService, PatientInvestigation as PatientInvestigationResponse } from 'generated';
import { maybeDate } from 'date';
import { PatientFileInvestigation } from './investigation';

const transformer = (value: PatientInvestigationResponse): PatientFileInvestigation => ({
    ...value,
    startedOn: maybeDate(value.startedOn)
});

const patientInvestigations = (patient: number) =>
    PatientFileService.investigations({ patientId: patient }).then((response) =>
        response ? response.map(transformer) : []
    );

export { patientInvestigations };
