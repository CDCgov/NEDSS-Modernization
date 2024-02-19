/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Concept } from '../models/Concept';
import type { CreateConceptRequest } from '../models/CreateConceptRequest';
import type { Page_Concept_ } from '../models/Page_Concept_';
import type { UpdateConceptRequest } from '../models/UpdateConceptRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConceptControllerService {

    /**
     * findConcepts
     * @returns Concept OK
     * @throws ApiError
     */
    public static findConceptsUsingGet({
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
     * createConcept
     * @returns Concept OK
     * @returns any Created
     * @throws ApiError
     */
    public static createConceptUsingPost({
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
        request: CreateConceptRequest,
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
     * searchConcepts
     * @returns Page_Concept_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchConceptsUsingPost({
        authorization,
        codeSetNm,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * codeSetNm
         */
        codeSetNm: string,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Concept_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/valueset/{codeSetNm}/concepts/search',
            path: {
                'codeSetNm': codeSetNm,
            },
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
     * updateConcept
     * @returns Concept OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateConceptUsingPut({
        authorization,
        codeSetNm,
        localCode,
        request,
    }: {
        authorization: string,
        /**
         * codeSetNm
         */
        codeSetNm: string,
        /**
         * localCode
         */
        localCode: string,
        /**
         * request
         */
        request: UpdateConceptRequest,
    }): CancelablePromise<Concept | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/valueset/{codeSetNm}/concepts/{localCode}',
            path: {
                'codeSetNm': codeSetNm,
                'localCode': localCode,
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
