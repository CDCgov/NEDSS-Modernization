/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UpdatePageDetailsRequest } from '../models/UpdatePageDetailsRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageControllerService {

    /**
     * updatePageDetails
     * @returns any OK
     * @throws ApiError
     */
    public static updatePageDetailsUsingPut({
        authorization,
        id,
        request,
    }: {
        authorization: any,
        /**
         * id
         */
        id: string,
        /**
         * request
         */
        request: UpdatePageDetailsRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/page-builder/api/v1/pages/{id}/details',
            path: {
                'id': id,
            },
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
