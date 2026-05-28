/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateFilterRequest } from './CreateFilterRequest';
export type AdminReportRequest = {
    dataSourceId: number;
    libraryId: number;
    reportTitle: string;
    sectionCode: string;
    ownerId: number;
    group: AdminReportRequest.group;
    filterRequests: Array<CreateFilterRequest>;
    description?: string;
};
export namespace AdminReportRequest {
    export enum group {
        PRIVATE = 'Private',
        REPORTING_FACILITY = 'Reporting Facility',
        PUBLIC = 'Public',
        TEMPLATE = 'Template',
    }
}

