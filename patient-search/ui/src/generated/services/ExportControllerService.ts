/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { InvestigationFilter } from '../models/InvestigationFilter';
import type { LabReportFilter } from '../models/LabReportFilter';
import type { Resource } from '../models/Resource';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ExportControllerService {

    /**
     * generateExportCsv
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateExportCsvUsingPost({
        authorization,
        filter,
    }: {
        authorization: any,
        /**
         * filter
         */
        filter: InvestigationFilter,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/investigations/export/csv',
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
     * generateExportPdf
     * @returns Resource OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateExportPdfUsingPost({
        authorization,
        filter,
    }: {
        authorization: any,
        /**
         * filter
         */
        filter: InvestigationFilter,
    }): CancelablePromise<Resource | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/investigations/export/pdf',
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
     * generateExportCsv
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateExportCsvUsingPost1({
        authorization,
        filter,
    }: {
        authorization: any,
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
     * generateExport
     * @returns Resource OK
     * @returns any Created
     * @throws ApiError
     */
    public static generateExportUsingPost({
        authorization,
        filter,
    }: {
        authorization: any,
        /**
         * filter
         */
        filter: LabReportFilter,
    }): CancelablePromise<Resource | any> {
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
