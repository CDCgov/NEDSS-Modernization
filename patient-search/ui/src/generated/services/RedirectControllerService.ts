/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class RedirectControllerService {

    /**
     * prepareAddLabReportOrInvestigation
     * @returns any OK
     * @throws ApiError
     */
    public static prepareAddLabReportOrInvestigationUsingGet({
        authorization,
        patientId,
    }: {
        authorization: any,
        /**
         * patientId
         */
        patientId: string,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/preparAddLabReportOrInvestigation',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'patientId': patientId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * preparePatientDetails
     * @returns any OK
     * @throws ApiError
     */
    public static preparePatientDetailsUsingGet({
        authorization,
    }: {
        authorization: any,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/preparePatientDetails',
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
