/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayableSimpleName } from './DisplayableSimpleName';
import type { ResultedTest } from './ResultedTest';
import type { Specimen } from './Specimen';
export type DocumentRequiringReview = {
    patient: number;
    id: number;
    local: string;
    type: string;
    eventDate?: string;
    dateReceived?: string;
    isElectronic?: boolean;
    isUpdate?: boolean;
    reportingFacility?: string;
    orderingFacility?: string;
    orderingProvider?: DisplayableSimpleName;
    sendingFacility?: string;
    condition?: string;
    specimen?: Specimen;
    treatments?: Array<string>;
    resultedTests?: Array<ResultedTest>;
};

