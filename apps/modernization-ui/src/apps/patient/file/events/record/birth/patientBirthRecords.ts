import { get, maybeJson } from 'libs/api';
import { mapOr } from 'utils/mapping';
import { MotherInformation, PatientFileBirthRecord } from './birth-record';
import { AssociatedInvestigation } from 'libs/events/investigations/associated';
import { maybeDate } from 'date';

type PatientFileBirthRecordResponse = {
    patient: number;
    id: number;
    local: string;
    receivedOn?: string;
    facility?: string;
    collectedOn?: string;
    certificate?: string;
    mother?: MotherInformation;
    associations?: AssociatedInvestigation[];
};

const transformer = (response: PatientFileBirthRecordResponse): PatientFileBirthRecord => ({
    ...response,
    receivedOn: maybeDate(response.receivedOn),
    collectedOn: maybeDate(response.collectedOn)
});

const patientBirthRecords = (patient: number): Promise<PatientFileBirthRecord[]> =>
    fetch(get(`/nbs/api/patients/${patient}/records/birth`))
        .then(maybeJson)
        .then(mapOr((response) => response.map(transformer), []))
        .catch(() => []);

export { patientBirthRecords };
