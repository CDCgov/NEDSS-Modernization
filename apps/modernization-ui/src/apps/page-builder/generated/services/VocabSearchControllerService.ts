/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ValueSetByOIDResponse } from '../models/ValueSetByOIDResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class VocabSearchControllerService {

    /**
     * fetchValueSetInfoByOID
     * @returns ValueSetByOIDResponse OK
     * @throws ApiError
     */
    public static fetchValueSetInfoByOidUsingGet({
        authorization,
        oid,
    }: {
        authorization: any,
        /**
         * oid
         */
        oid: string,
    }): CancelablePromise<ValueSetByOIDResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/page-builder/api/v1/phin/oid/{oid}',
            path: {
                'oid': oid,
            },
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

}
