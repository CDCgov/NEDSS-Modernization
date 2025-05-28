/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayableSimpleName } from './DisplayableSimpleName';
import type { ResultedTest } from './ResultedTest';
export type DocumentRequiringReview = {
    id?: number;
    local?: string;
    type?: string;
    eventDate?: string;
    dateReceived?: string;
    isElectronic?: boolean;
    isUpdate?: boolean;
    reportingFacility?: string;
    orderingProvider?: DisplayableSimpleName;
    sendingFacility?: string;
    condition?: string;
    treatments?: Array<string>;
    resultedTests?: Array<ResultedTest>;
};

