/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ReportExecutionRequest } from './ReportExecutionRequest';
export type SaveAsReportRequest = {
    reportTitle: string;
    sectionCode: string;
    group: SaveAsReportRequest.group;
    executionRequest: ReportExecutionRequest;
    description?: string;
};
export namespace SaveAsReportRequest {
    export enum group {
        PRIVATE = 'PRIVATE',
        REPORTING_FACILITY = 'REPORTING_FACILITY',
        PUBLIC = 'PUBLIC',
        TEMPLATE = 'TEMPLATE',
    }
}

