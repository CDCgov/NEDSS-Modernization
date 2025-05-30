/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Selectable } from './Selectable';
export type PatientPhoneDemographic = {
    identifier: number;
    asOf: string;
    type: Selectable;
    use: Selectable;
    countryCode?: string;
    phoneNumber?: string;
    extension?: string;
    email?: string;
    url?: string;
    comment?: string;
};

