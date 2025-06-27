/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { DisplayableSimpleName } from './DisplayableSimpleName';
import type { ResultedTest } from './ResultedTest';
import type { Specimen } from './Specimen';
export type PatientLabReport = {
    patient: number;
    id: number;
    local: string;
    programArea: string;
    jurisdiction: string;
    receivedDate?: string;
    electronic: boolean;
    processingDecision?: string;
    collectedDate?: string;
    resultedTests?: Array<ResultedTest>;
    reportingFacility?: string;
    orderingProvider?: DisplayableSimpleName;
    orderingFacility?: string;
    specimen?: Specimen;
    associations?: Array<AssociatedInvestigation>;
};

