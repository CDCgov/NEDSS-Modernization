/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MostRecentLegalName } from './MostRecentLegalName';
export type PatientFileHeader = {
    id: number;
    patientId: string;
    local: string;
    status: string;
    deletablity: string;
    sex: string;
    birthday: string;
    name?: MostRecentLegalName;
};

