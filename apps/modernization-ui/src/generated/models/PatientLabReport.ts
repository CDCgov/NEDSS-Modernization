/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { DisplayableSimpleName } from './DisplayableSimpleName';
import type { ResultedTest } from './ResultedTest';
export type PatientLabReport = {
    eventId: string;
    receivedDate?: string;
    processingDecision?: string;
    collectedDate?: string;
    testResults?: Array<ResultedTest>;
    associatedInvestigation?: AssociatedInvestigation;
    programArea?: string;
    jurisdiction: string;
    id?: number;
    specimenSource?: string;
    reportingFacility?: string;
    orderingProvider?: DisplayableSimpleName;
    orderingFacility?: string;
};

