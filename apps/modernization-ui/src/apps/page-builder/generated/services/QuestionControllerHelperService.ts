/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayControlOptions } from '../models/DisplayControlOptions';
import type { QuestionValidationRequest } from '../models/QuestionValidationRequest';
import type { QuestionValidationResponse } from '../models/QuestionValidationResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class QuestionControllerHelperService {

    /**
     * getDisplayControlOptions
     * @returns DisplayControlOptions OK
     * @throws ApiError
     */
    public static getDisplayControlOptionsUsingGet({
        authorization,
    }: {
        authorization: string,
    }): CancelablePromise<DisplayControlOptions> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/questions/displayControlOptions',
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

    /**
     * validate
     * @returns QuestionValidationResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static validateUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: QuestionValidationRequest,
    }): CancelablePromise<QuestionValidationResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/questions/validate',
            headers: {
                'Authorization': authorization,
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
