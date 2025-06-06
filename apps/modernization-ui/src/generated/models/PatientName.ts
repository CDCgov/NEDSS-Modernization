/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Selectable } from './Selectable';
export type PatientName = {
    identifier: number;
    asOf: string;
    type: Selectable;
    prefix?: Selectable;
    first?: string;
    middle?: string;
    last?: string;
    suffix?: Selectable;
    degree?: Selectable;
};

