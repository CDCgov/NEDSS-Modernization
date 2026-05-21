/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateFilterRequest } from './CreateFilterRequest';
export type CreateReportRequest = {
    dataSourceId: number;
    libraryId: number;
    reportTitle: string;
    sectionCode: string;
    description?: string;
    ownerId?: string;
    reportFilters?: Array<CreateFilterRequest>;
};

