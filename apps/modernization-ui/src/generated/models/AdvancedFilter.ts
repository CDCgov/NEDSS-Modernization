/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Clause } from './Clause';
import type { Connector } from './Connector';
import type { Filter } from './Filter';
export type AdvancedFilter = (Filter & {
    isBasic?: boolean;
    logic?: (Clause | Connector);
} & {
    isBasic: boolean;
    logic: (Clause | Connector);
});

