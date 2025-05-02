/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayableAddress } from './DisplayableAddress';
import type { DisplayableIdentification } from './DisplayableIdentification';
import type { DisplayablePhone } from './DisplayablePhone';
export type PatientDemographicsSummary = {
    address?: DisplayableAddress;
    phone?: DisplayablePhone;
    email?: string;
    ethnicity?: string;
    identifications?: Array<DisplayableIdentification>;
    races?: Array<string>;
};

