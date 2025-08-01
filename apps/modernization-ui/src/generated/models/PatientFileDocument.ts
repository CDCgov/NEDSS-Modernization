/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
export type PatientFileDocument = {
    patient: number;
    id: number;
    local: string;
    receivedOn?: string;
    sendingFacility?: string;
    reportedOn?: string;
    condition?: string;
    updated?: boolean;
    associations?: Array<AssociatedInvestigation>;
};

