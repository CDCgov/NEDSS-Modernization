/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { InvestigationFilter } from '../models/InvestigationFilter';
import type { LabReportFilter } from '../models/LabReportFilter';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ExportControllerService {

    /**
     * generateInvestigationCsv
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateInvestigationCsvUsingPost({
        authorization,
        filter,
    }: {
        authorization: string,
        /**
         * filter
         */
        filter: InvestigationFilter,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/investigation/export/csv',
            headers: {
                'Authorization': authorization,
            },
            body: filter,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * generateInvestigationPdf
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateInvestigationPdfUsingPost({
        authorization,
        filter,
    }: {
        authorization: string,
        /**
         * filter
         */
        filter: InvestigationFilter,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/investigation/export/pdf',
            headers: {
                'Authorization': authorization,
            },
            body: filter,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * generateLabReportCsv
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateLabReportCsvUsingPost({
        authorization,
        filter,
    }: {
        authorization: string,
        /**
         * filter
         */
        filter: LabReportFilter,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/labreport/export/csv',
            headers: {
                'Authorization': authorization,
            },
            body: filter,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * generateLabReportPdf
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateLabReportPdfUsingPost({
        authorization,
        filter,
    }: {
        authorization: string,
        /**
         * filter
         */
        filter: LabReportFilter,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/labreport/export/pdf',
            headers: {
                'Authorization': authorization,
            },
            body: filter,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
