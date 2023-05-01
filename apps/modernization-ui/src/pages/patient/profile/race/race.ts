export type Race = {
    __typename?: 'PatientRace';
    patient: number;
    id: string;
    version: number;
    asOf: any;
    category: { __typename?: 'PatientCodedValue'; id: string; description: string };
    detailed?: Array<{ __typename?: 'PatientCodedValue'; id: string; description: string } | null> | null;
} | null;
