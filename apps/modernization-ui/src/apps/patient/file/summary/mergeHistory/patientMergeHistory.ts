import { PatientFileMergeHistory } from './mergeHistory';

const dummyMergeHistory: PatientFileMergeHistory[] = [
    {
        supersededPersonLocalId: '91001',
        supersededPersonLegalName: 'Loki Laufeyson',
        mergeTimestamp: '07/14/2021 2:33 pm',
        mergedByUser: 'admin_user'
    },
    {
        supersededPersonLocalId: '95001',
        supersededPersonLegalName: 'Bob The Builder',
        mergeTimestamp: '07/14/2021 2:33 pm',
        mergedByUser: 'EpiDoe'
    },
    {
        supersededPersonLocalId: '91002',
        supersededPersonLegalName: 'Humpty Dumpty',
        mergeTimestamp: '07/14/2022 2:00 pm',
        mergedByUser: 'EpiDoe'
    },
    {
        supersededPersonLocalId: '90059',
        supersededPersonLegalName: 'Alice Wonderland',
        mergeTimestamp: '07/14/2022 2:00 pm',
        mergedByUser: 'EpiDoe'
    },
    {
        supersededPersonLocalId: '90082',
        supersededPersonLegalName: 'Queen Hearts',
        mergeTimestamp: '07/14/2022 2:00 pm',
        mergedByUser: 'EpiDoe'
    }
];

const patientMergeHistory = async (): Promise<PatientFileMergeHistory[]> => {
    return Promise.resolve(dummyMergeHistory);
};

export { patientMergeHistory };
