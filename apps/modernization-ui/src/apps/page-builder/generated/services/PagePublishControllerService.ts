/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PagePublishRequest } from '../models/PagePublishRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PagePublishControllerService {

    /**
     * publishPage
     * @returns any OK
     * @throws ApiError
     */
    public static publishPageUsingPut({
        authorization,
        id,
        request,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: PagePublishRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{id}/publish',
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
