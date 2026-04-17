/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Clause } from './Clause';
import type { Connector } from './Connector';
export type AdvancedFilter = {
    reportFilterUid: number;
    logic: (Clause | Connector);
};

