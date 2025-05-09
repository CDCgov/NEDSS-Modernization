/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { InvestigatorName } from './InvestigatorName';
export type PatientInvestigation = {
    investigationId: string;
    identifier: number;
    startedOn?: string;
    condition: string;
    status: string;
    caseStatus?: string;
    jurisdiction: string;
    coInfection?: string;
    notification?: string;
    investigatorName?: InvestigatorName;
    comparable: boolean;
};

