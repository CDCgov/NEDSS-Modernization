/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AdvancedFilterConfiguration } from './AdvancedFilterConfiguration';
import type { BasicFilterConfiguration } from './BasicFilterConfiguration';
import type { Library } from './Library';
import type { ReportColumn } from './ReportColumn';
import type { ReportDataSource } from './ReportDataSource';
import type { SortSpec } from './SortSpec';
export type ReportConfiguration = {
    dataSource: ReportDataSource;
    library: Library;
    title: string;
    description?: string;
    ownerUid: number;
    group: ReportConfiguration.group;
    sectionCd: string;
    basicFilters: Array<BasicFilterConfiguration>;
    advancedFilter?: AdvancedFilterConfiguration;
    columns: Array<ReportColumn>;
    defaultColumnUids?: Array<number>;
    defaultSort?: SortSpec;
};
export namespace ReportConfiguration {
    export enum group {
        PRIVATE = 'PRIVATE',
        REPORTING_FACILITY = 'REPORTING_FACILITY',
        PUBLIC = 'PUBLIC',
        TEMPLATE = 'TEMPLATE',
    }
}

