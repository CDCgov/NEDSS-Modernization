/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { FacilityProviders } from './FacilityProviders';
import type { TestResult } from './TestResult';
export type PatientLabReport = {
    eventId: string;
    receivedDate?: string;
    processingDecision?: string;
    facilityProviders?: FacilityProviders;
    collectedDate?: string;
    testResult?: TestResult;
    associatedInvestigation?: AssociatedInvestigation;
    programArea?: string;
    jurisdiction: string;
    labIdentifier?: string;
};

