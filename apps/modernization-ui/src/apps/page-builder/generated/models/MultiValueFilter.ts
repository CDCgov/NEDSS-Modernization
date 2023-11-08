/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Filter } from './Filter';

export type MultiValueFilter = (Filter & {
    operator: MultiValueFilter.operator;
    property: string;
    values: Array<string>;
});

export namespace MultiValueFilter {

    export enum operator {
        CONTAINS = 'CONTAINS',
        EQUALS = 'EQUALS',
        NOT_EQUAL_TO = 'NOT_EQUAL_TO',
        STARTS_WITH = 'STARTS_WITH',
    }


}

