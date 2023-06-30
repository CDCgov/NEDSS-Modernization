/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateValueSetResponse } from '../models/CreateValueSetResponse';
import type { Page_GetValueSet_ } from '../models/Page_GetValueSet_';
import type { ValueSetRequest } from '../models/ValueSetRequest';
import type { ValueSetSearchRequest } from '../models/ValueSetSearchRequest';
import type { ValueSetStateChangeResponse } from '../models/ValueSetStateChangeResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ValueSetControllerService {

    /**
     * findAllValueSets
     * @returns Page_GetValueSet_ OK
     * @throws ApiError
     */
    public static findAllValueSetsUsingGet({
        authorization,
        page,
        size,
        sort,
    }: {
        authorization: any,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_GetValueSet_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/pageBuilder/api/v1/valueset/',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * createValueSet
     * @returns CreateValueSetResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createValueSetUsingPost({
        authorization,
        request,
    }: {
        authorization: any,
        /**
         * request
         */
        request: ValueSetRequest,
    }): CancelablePromise<CreateValueSetResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/pageBuilder/api/v1/valueset/',
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

    /**
     * searchValueSearch
     * @returns Page_GetValueSet_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchValueSearchUsingPost({
        authorization,
        search,
        page,
        size,
        sort,
    }: {
        authorization: any,
        /**
         * search
         */
        search: ValueSetSearchRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_GetValueSet_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/pageBuilder/api/v1/valueset/search',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: search,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * deleteValueSet
     * @returns ValueSetStateChangeResponse OK
     * @throws ApiError
     */
    public static deleteValueSetUsingDelete({
        authorization,
        codeSetNm,
    }: {
        authorization: any,
        /**
         * codeSetNm
         */
        codeSetNm: string,
    }): CancelablePromise<ValueSetStateChangeResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/pageBuilder/api/v1/valueset/{codeSetNm}',
            path: {
                'codeSetNm': codeSetNm,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }

    /**
     * activateValueSet
     * @returns ValueSetStateChangeResponse OK
     * @throws ApiError
     */
    public static activateValueSetUsingPatch({
        authorization,
        codeSetNm,
    }: {
        authorization: any,
        /**
         * codeSetNm
         */
        codeSetNm: string,
    }): CancelablePromise<ValueSetStateChangeResponse> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/pageBuilder/api/v1/valueset/{codeSetNm}',
            path: {
                'codeSetNm': codeSetNm,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }

}
