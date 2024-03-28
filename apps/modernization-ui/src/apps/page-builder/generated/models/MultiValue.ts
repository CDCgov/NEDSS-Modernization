/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FilterJSON } from './FilterJSON';
export type MultiValue = (FilterJSON & {
    property?: string;
    operator?: MultiValue.operator;
    values?: Array<string>;
} & {
    property: string;
    operator: MultiValue.operator;
    values: Array<string>;
});
export namespace MultiValue {
    export enum operator {
        EQUALS = 'EQUALS',
        NOT_EQUAL_TO = 'NOT_EQUAL_TO',
        STARTS_WITH = 'STARTS_WITH',
        CONTAINS = 'CONTAINS',
    }
}

