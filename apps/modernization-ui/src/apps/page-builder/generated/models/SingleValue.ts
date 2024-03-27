/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { FilterJSON } from './FilterJSON';

export type SingleValue = (FilterJSON & {
    property?: string;
    operator?: SingleValue.operator;
    value?: string;
} & {
    property: string;
    operator: SingleValue.operator;
    value: string;
});

export namespace SingleValue {

    export enum operator {
        EQUALS = 'EQUALS',
        NOT_EQUAL_TO = 'NOT_EQUAL_TO',
        STARTS_WITH = 'STARTS_WITH',
        CONTAINS = 'CONTAINS',
    }


}

