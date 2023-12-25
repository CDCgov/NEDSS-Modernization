import { PatientIdentificationAuthority, PatientIdentificationType } from 'generated/graphql/schema';

export type IdentificationEntry = {
    patient: number;
    asOf: string | null;
    type: string | null;
    value: string | null;
    state: string | null;
    sequence?: number;
};

export enum Column {
    AsOf = 'As of',
    Type = 'Type',
    Authority = 'Authority',
    Value = 'Value',
    Actions = 'Actions'
}

export type Identification = {
    asOf: Date;
    type: PatientIdentificationType;
    authority?: PatientIdentificationAuthority | null;
    value?: string | null;
    patient: number;
    sequence: number;
    version: number | null;
};
