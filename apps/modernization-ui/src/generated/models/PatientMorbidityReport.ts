/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { DisplayableSimpleName } from './DisplayableSimpleName';
import type { ResultedTest } from './ResultedTest';
export type PatientMorbidityReport = {
    patient: number;
    id: number;
    local: string;
    jurisdiction: string;
    addedOn: string;
    receivedOn?: string;
    reportedOn?: string;
    condition?: string;
    reportingFacility?: string;
    orderingProvider?: DisplayableSimpleName;
    reportingProvider?: DisplayableSimpleName;
    treatments?: Array<string>;
    resultedTests?: Array<ResultedTest>;
    associations?: Array<AssociatedInvestigation>;
};

