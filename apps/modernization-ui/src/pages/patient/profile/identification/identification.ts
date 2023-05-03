import { Maybe, PatientCodedValue } from 'generated/graphql/schema';

export type Identification = {
    __typename?: 'PatientIdentification';
    patient: number;
    id: string;
    sequence: number;
    version: number;
    asOf: any;
    value?: string | null;
    authority?: { __typename?: 'PatientCodedValue'; id: string; description: string } | null;
    type?: Maybe<PatientCodedValue>;
} | null;
