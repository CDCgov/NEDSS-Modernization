/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddConceptRequest } from '../models/AddConceptRequest';
import type { Concept } from '../models/Concept';
import type { CreateValueSetResponse } from '../models/CreateValueSetResponse';
import type { Page_ValueSet_ } from '../models/Page_ValueSet_';
import type { UpdateConceptRequest } from '../models/UpdateConceptRequest';
import type { UpdatedValueSetResponse } from '../models/UpdatedValueSetResponse';
import type { ValueSetRequest } from '../models/ValueSetRequest';
import type { ValueSetSearchRequest } from '../models/ValueSetSearchRequest';
import type { ValueSetStateChangeResponse } from '../models/ValueSetStateChangeResponse';
import type { ValueSetUpdateRequest } from '../models/ValueSetUpdateRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ValueSetControllerService {

    /**
     * findAllValueSets
     * @returns Page_ValueSet_ OK
     * @throws ApiError
     */
    public static findAllValueSetsUsingGet({
        authorization,
        page,
        size,
        sort,
    }: {
        authorization: string,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_ValueSet_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/valueset',
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
        authorization: string,
        /**
         * request
         */
        request: ValueSetRequest,
    }): CancelablePromise<CreateValueSetResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/valueset',
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
     * searchValueSet
     * @returns Page_ValueSet_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchValueSetUsingPost({
        authorization,
        search,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * search
         */
        search: ValueSetSearchRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_ValueSet_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/valueset/search',
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
     * updateValueSet
     * @returns UpdatedValueSetResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateValueSetUsingPost({
        authorization,
        update,
    }: {
        authorization: string,
        /**
         * update
         */
        update: ValueSetUpdateRequest,
    }): CancelablePromise<UpdatedValueSetResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/valueset/update',
            headers: {
                'Authorization': authorization,
            },
            body: update,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * activateValueSet
     * @returns ValueSetStateChangeResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static activateValueSetUsingPut({
        authorization,
        codeSetNm,
    }: {
        authorization: string,
        /**
         * codeSetNm
         */
        codeSetNm: string,
    }): CancelablePromise<ValueSetStateChangeResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/valueset/{codeSetNm}/activate',
            path: {
                'codeSetNm': codeSetNm,
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

    /**
     * findConceptsByCodeSetName
     * @returns Concept OK
     * @throws ApiError
     */
    public static findConceptsByCodeSetNameUsingGet({
        authorization,
        codeSetNm,
    }: {
        authorization: string,
        /**
         * codeSetNm
         */
        codeSetNm: string,
    }): CancelablePromise<Array<Concept>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/valueset/{codeSetNm}/concepts',
            path: {
                'codeSetNm': codeSetNm,
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

    /**
     * addConcept
     * @returns Concept OK
     * @returns any Created
     * @throws ApiError
     */
    public static addConceptUsingPost({
        authorization,
        codeSetNm,
        request,
    }: {
        authorization: string,
        /**
         * codeSetNm
         */
        codeSetNm: string,
        /**
         * request
         */
        request: AddConceptRequest,
    }): CancelablePromise<Concept | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/valueset/{codeSetNm}/concepts',
            path: {
                'codeSetNm': codeSetNm,
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

    /**
     * updateConcept
     * @returns Concept OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateConceptUsingPut({
        authorization,
        codeSetNm,
        conceptCode,
        request,
    }: {
        authorization: string,
        /**
         * codeSetNm
         */
        codeSetNm: string,
        /**
         * conceptCode
         */
        conceptCode: string,
        /**
         * request
         */
        request: UpdateConceptRequest,
    }): CancelablePromise<Concept | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/valueset/{codeSetNm}/concepts/{conceptCode}',
            path: {
                'codeSetNm': codeSetNm,
                'conceptCode': conceptCode,
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

    /**
     * deleteValueSet
     * @returns ValueSetStateChangeResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static deleteValueSetUsingPut({
        authorization,
        codeSetNm,
    }: {
        authorization: string,
        /**
         * codeSetNm
         */
        codeSetNm: string,
    }): CancelablePromise<ValueSetStateChangeResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/valueset/{codeSetNm}/deactivate',
            path: {
                'codeSetNm': codeSetNm,
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
