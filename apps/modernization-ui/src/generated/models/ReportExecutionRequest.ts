/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AdvancedFilterRequest } from './AdvancedFilterRequest';
import type { BasicFilterRequest } from './BasicFilterRequest';
export type ReportExecutionRequest = {
    reportUid: number;
    dataSourceUid: number;
    isExport: boolean;
    columnUids?: Array<number>;
    sortColumnUid?: number;
    sortDirection?: ReportExecutionRequest.sortDirection;
    basicFilters?: Array<BasicFilterRequest>;
    advancedFilter?: AdvancedFilterRequest;
};
export namespace ReportExecutionRequest {
    export enum sortDirection {
        ASC = 'ASC',
        DESC = 'DESC',
    }
}

