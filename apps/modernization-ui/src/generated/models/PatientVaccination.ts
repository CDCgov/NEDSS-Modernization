/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { DisplayableSimpleName } from './DisplayableSimpleName';
export type PatientVaccination = {
    patient: number;
    id: number;
    local: string;
    createdOn?: string;
    organization?: string;
    provider?: DisplayableSimpleName;
    administeredOn?: string;
    administered?: string;
    associations?: Array<AssociatedInvestigation>;
};

