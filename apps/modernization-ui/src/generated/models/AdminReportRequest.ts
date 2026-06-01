/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UpsertFilterRequest } from './UpsertFilterRequest';
export type AdminReportRequest = {
    dataSourceId: number;
    libraryId: number;
    reportTitle: string;
    sectionCode: string;
    ownerId: number;
    group: AdminReportRequest.group;
    filterRequests: Array<UpsertFilterRequest>;
    description?: string;
};
export namespace AdminReportRequest {
    export enum group {
        PRIVATE = 'PRIVATE',
        REPORTING_FACILITY = 'REPORTING_FACILITY',
        PUBLIC = 'PUBLIC',
        TEMPLATE = 'TEMPLATE',
    }
}

