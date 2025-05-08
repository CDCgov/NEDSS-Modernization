/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayableName } from './DisplayableName';
export type PatientFile = {
    id?: number;
    patientId?: number;
    local?: string;
    status?: string;
    deletablity?: PatientFile.deletablity;
    sex?: string;
    birthday?: string;
    deceasedOn?: string;
    name?: DisplayableName;
};
export namespace PatientFile {
    export enum deletablity {
        DELETABLE = 'Deletable',
        IS_INACTIVE = 'Is_Inactive',
        HAS_ASSOCIATIONS = 'Has_Associations',
    }
}

