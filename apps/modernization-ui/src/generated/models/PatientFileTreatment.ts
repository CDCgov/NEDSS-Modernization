/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { DisplayableSimpleName } from './DisplayableSimpleName';
export type PatientFileTreatment = {
    patient: number;
    id: number;
    local: string;
    createdOn?: string;
    treatedOn?: string;
    description?: string;
    organization?: string;
    provider?: DisplayableSimpleName;
    associations?: Array<AssociatedInvestigation>;
};

