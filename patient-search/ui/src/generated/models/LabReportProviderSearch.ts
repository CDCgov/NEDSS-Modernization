/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type LabReportProviderSearch = {
    providerId?: number;
    providerType?: LabReportProviderSearch.providerType;
};

export namespace LabReportProviderSearch {

    export enum providerType {
        ORDERING_FACILITY = 'ORDERING_FACILITY',
        ORDERING_PROVIDER = 'ORDERING_PROVIDER',
        REPORTING_FACILITY = 'REPORTING_FACILITY',
    }


}

