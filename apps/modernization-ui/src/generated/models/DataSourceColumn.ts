/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataSource } from './DataSource';
import type { EffectiveTime } from './EffectiveTime';
export type DataSourceColumn = {
    id?: number;
    columnMaxLength?: number;
    columnName?: string;
    columnTitle?: string;
    columnSourceTypeCode?: string;
    dataSource?: DataSource;
    descTxt?: string;
    displayable?: string;
    effectiveTime?: EffectiveTime;
    filterable?: string;
    statusCd?: string;
    statusTime?: string;
};

