/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AdvancedFilter } from './AdvancedFilter';
import type { BasicFilter } from './BasicFilter';
import type { TimeRange } from './TimeRange';
export type ReportExecutionRequest = {
    reportUid: number;
    dataSourceUid: number;
    isExport: boolean;
    reportTitle?: string;
    columnUids?: Array<number>;
    filters?: Array<(AdvancedFilter | BasicFilter)>;
    timeRange?: TimeRange;
};

