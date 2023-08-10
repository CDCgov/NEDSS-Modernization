/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateTabRequest } from '../models/CreateTabRequest';
import type { CreateUiResponse } from '../models/CreateUiResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AddTabControllerService {

    /**
     * createTab
     * @returns CreateUiResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createTabUsingPost({
        authorization,
        request,
    }: {
        authorization: any,
        /**
         * request
         */
        request: CreateTabRequest,
    }): CancelablePromise<CreateUiResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/addtab',
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
