/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { FacilityProviders } from './FacilityProviders';
import type { ResultedTest } from './ResultedTest';
export type PatientLabReport = {
    eventId: string;
    receivedDate?: string;
    processingDecision?: string;
    facilityProviders?: FacilityProviders;
    collectedDate?: string;
    testResults?: Array<ResultedTest>;
    associatedInvestigation?: AssociatedInvestigation;
    programArea?: string;
    jurisdiction: string;
    id?: number;
    specimenSource?: string;
};

