/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Filter } from './Filter';

export type SingleValueFilter = (Filter & {
    operator: SingleValueFilter.operator;
    property: string;
    value: string;
});

export namespace SingleValueFilter {

    export enum operator {
        CONTAINS = 'CONTAINS',
        EQUALS = 'EQUALS',
        NOT_EQUAL_TO = 'NOT_EQUAL_TO',
        STARTS_WITH = 'STARTS_WITH',
    }


}

