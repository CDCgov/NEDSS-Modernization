import { PatientFileMergeHistory } from './mergeHistory';

const dummyMergeHistory: PatientFileMergeHistory[] = [
    {
        supersededPersonLocalId: '98001',
        supersededPersonLegalName: 'Loki Laufeyson',
        mergeTimestamp: '07/14/2021 2:33 pm',
        mergedByUser: 'admin_user'
    },
    {
        supersededPersonLocalId: '95001',
        supersededPersonLegalName: 'Bob The Builder',
        mergeTimestamp: '07/14/2021 2:33 pm',
        mergedByUser: 'EpiDoe'
    }
];

const patientMergeHistory = async (): Promise<PatientFileMergeHistory[]> => {
    return Promise.resolve(dummyMergeHistory);
};

export { patientMergeHistory };
