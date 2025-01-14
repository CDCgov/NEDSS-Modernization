import { PatientNameDegree, PatientNamePrefix, PatientNameSuffix, PatientNameUse } from 'generated/graphql/schema';

export type NameEntry = {
    patient?: number;
    sequence?: number | null;
    asOf: string | null;
    type: string | null;
    prefix: string | null;
    first: string | null;
    middle: string | null;
    secondMiddle: string | null;
    last: string | null;
    secondLast: string | null;
    suffix: string | null;
    degree: string | null;
};

export enum Column {
    AsOf = 'As of',
    Use = 'Type',
    Prefix = 'Prefix',
    Name = 'Name (last, first middle)',
    Suffix = 'Suffix',
    Degree = 'Degree',
    Actions = 'Actions'
}

export type Name = {
    asOf: Date;
    use: PatientNameUse;
    prefix?: PatientNamePrefix | null;
    first?: string | null;
    middle?: string | null;
    secondMiddle?: string | null;
    last?: string | null;
    secondLast?: string | null;
    suffix?: PatientNameSuffix | null;
    degree?: PatientNameDegree | null;
    patient: number;
    sequence: number;
    version: number | null;
};
