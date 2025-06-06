import { maybeMap } from 'utils/mapping';
import { PatientInvestigation as PatientInvestigationResponse } from 'generated';
import { PatientInvestigation } from './PatientInvestigation';

const maybeDate = maybeMap((value: string) => new Date(value));

const transformer = (value: PatientInvestigationResponse): PatientInvestigation => ({
    ...value,
    startedOn: maybeDate(value.startedOn)
});

export { transformer };
