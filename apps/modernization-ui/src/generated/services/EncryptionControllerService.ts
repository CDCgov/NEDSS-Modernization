/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EncryptionResponse } from '../models/EncryptionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class EncryptionControllerService {

    /**
     * @returns EncryptionResponse OK
     * @throws ApiError
     */
    public static encrypt({
        requestBody,
    }: {
        requestBody: any,
    }): CancelablePromise<EncryptionResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/encryption/encrypt',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static decrypt({
        requestBody,
    }: {
        requestBody: string,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/encryption/decrypt',
            body: requestBody,
            mediaType: 'text/plain',
        });
    }

}
