/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSubSectionRequest } from '../models/CreateSubSectionRequest';
import type { CreateSubSectionResponse } from '../models/CreateSubSectionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AddSubSectionControllerService {

    /**
     * createSubSection
     * @returns CreateSubSectionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createSubSectionUsingPost({
        authorization,
        request,
    }: {
        authorization: any,
        /**
         * request
         */
        request: CreateSubSectionRequest,
    }): CancelablePromise<CreateSubSectionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/addsubsection',
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
