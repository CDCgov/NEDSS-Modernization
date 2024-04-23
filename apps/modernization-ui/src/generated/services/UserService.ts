/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Me } from '../models/Me';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class UserService {
    /**
     * Current User
     * Provides details about the user associated with the request.
     * @returns Me OK
     * @throws ApiError
     */
    public static me(): CancelablePromise<Me> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/me',
        });
    }
}
