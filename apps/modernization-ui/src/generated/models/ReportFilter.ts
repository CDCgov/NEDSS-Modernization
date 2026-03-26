/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataSourceColumn } from './DataSourceColumn';
import type { FilterCode } from './FilterCode';
import type { FilterValue } from './FilterValue';
import type { Report } from './Report';
export type ReportFilter = {
    id?: number;
    report?: Report;
    filterCode?: FilterCode;
    dataSourceColumn?: DataSourceColumn;
    filterValues?: Array<FilterValue>;
    statusCd?: string;
    maxValueCnt?: number;
    minValueCnt?: number;
};

