export type PatientRace = {
    __typename?: 'PatientRace';
    patient: number;
    version: number;
    asOf: any;
    category: { __typename?: 'PatientCodedValue'; id: string; description: string };
    detailed?: Array<{ __typename?: 'PatientCodedValue'; id: string; description: string }>;
};
