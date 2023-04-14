/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EncryptionResponse } from '../models/EncryptionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class EncryptionControllerService {

    /**
     * decrypt
     * @returns any OK
     * @throws ApiError
     */
    public static decryptUsingPost({
        authorization,
        encryptedString,
    }: {
        authorization: string,
        /**
         * encryptedString
         */
        encryptedString: string,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/encryption/decrypt',
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
        authorization: string,
        /**
         * object
         */
        object: any,
    }): CancelablePromise<EncryptionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/encryption/encrypt',
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
