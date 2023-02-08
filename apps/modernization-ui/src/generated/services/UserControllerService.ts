/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { LoginRequest } from '../models/LoginRequest';
import type { LoginResponse } from '../models/LoginResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class UserControllerService {

    /**
     * login
     * @returns LoginResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static loginUsingPost({
        request,
    }: {
        /**
         * request
         */
        request: LoginRequest,
    }): CancelablePromise<LoginResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/login',
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
