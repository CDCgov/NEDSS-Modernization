/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayableSimpleName } from './DisplayableSimpleName';
export type PatientInvestigation = {
    patient: number;
    identifier: number;
    local: string;
    startedOn?: string;
    condition: string;
    status: string;
    caseStatus?: string;
    jurisdiction: string;
    coInfection?: string;
    notification?: string;
    investigator?: DisplayableSimpleName;
    comparable: boolean;
};

