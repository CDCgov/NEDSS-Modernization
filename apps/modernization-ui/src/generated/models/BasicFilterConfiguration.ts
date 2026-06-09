/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FilterType } from './FilterType';
export type BasicFilterConfiguration = {
    reportFilterUid: number;
    reportColumnUid?: number;
    defaultValues?: Array<string>;
    defaultIncludeNulls: boolean;
    selectType?: BasicFilterConfiguration.selectType;
    isRequired: boolean;
    filterType: FilterType;
};
export namespace BasicFilterConfiguration {
    export enum selectType {
        SINGLE = 'SINGLE',
        MULTI = 'MULTI',
    }
}

