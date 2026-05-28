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
    group: string;
    filterRequests: Array<UpsertFilterRequest>;
    description?: string;
};

