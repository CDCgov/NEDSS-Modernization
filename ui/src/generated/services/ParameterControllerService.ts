/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EncryptionResponse } from '../models/EncryptionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ParameterControllerService {

    /**
     * decrypt
     * @returns any OK
     * @throws ApiError
     */
    public static decryptUsingPost({
        authorization,
        encryptedString,
    }: {
        authorization: any,
        /**
         * encryptedString
         */
        encryptedString: string,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/parameter/decrypt',
            headers: {
                'Authorization': authorization,
            },
            body: encryptedString,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * encrypt
     * @returns EncryptionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static encryptUsingPost({
        authorization,
        object,
    }: {
        authorization: any,
        /**
         * object
         */
        object: any,
    }): CancelablePromise<EncryptionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/parameter/encrypt',
            headers: {
                'Authorization': authorization,
            },
            body: object,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
