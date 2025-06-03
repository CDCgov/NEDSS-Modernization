/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Selectable } from './Selectable';
export type PatientAddressDemographic = {
    identifier: number;
    asOf: string;
    type: Selectable;
    use: Selectable;
    address1?: string;
    address2?: string;
    city?: string;
    state?: Selectable;
    zipcode?: string;
    county?: Selectable;
    censusTract?: string;
    country?: Selectable;
    comment?: string;
};

