/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Filter } from './Filter';

export type DateFilter = (Filter & {
    from: string;
    operator: DateFilter.operator;
    property: string;
});

export namespace DateFilter {

    export enum operator {
        LAST_14_DAYS = 'LAST_14_DAYS',
        LAST_30_DAYS = 'LAST_30_DAYS',
        LAST_7_DAYS = 'LAST_7_DAYS',
        MORE_THAN_30_DAYS = 'MORE_THAN_30_DAYS',
        TODAY = 'TODAY',
    }


}

