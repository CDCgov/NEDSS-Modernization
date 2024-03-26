/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { FilterJSON } from './FilterJSON';

export type Date = (FilterJSON & {
    property?: string;
    operator?: Date.operator;
} & {
    property: string;
    operator: Date.operator;
});

export namespace Date {

    export enum operator {
        TODAY = 'TODAY',
        LAST_7_DAYS = 'LAST_7_DAYS',
        LAST_14_DAYS = 'LAST_14_DAYS',
        LAST_30_DAYS = 'LAST_30_DAYS',
        MORE_THAN_30_DAYS = 'MORE_THAN_30_DAYS',
    }


}

