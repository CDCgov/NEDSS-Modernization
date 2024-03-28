/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Concept } from '../models/Concept';
import type { CreateConceptRequest } from '../models/CreateConceptRequest';
import type { PageConcept } from '../models/PageConcept';
import type { UpdateConceptRequest } from '../models/UpdateConceptRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ConceptControllerService {
    /**
     * @returns Concept OK
     * @throws ApiError
     */
    public static updateConcept({
        codeSetNm,
        localCode,
        requestBody,
    }: {
        codeSetNm: string,
        localCode: string,
        requestBody: UpdateConceptRequest,
    }): CancelablePromise<Concept> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/valueset/{codeSetNm}/concepts/{localCode}',
            path: {
                'codeSetNm': codeSetNm,
                'localCode': localCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Concept OK
     * @throws ApiError
     */
    public static findConcepts({
        codeSetNm,
    }: {
        codeSetNm: string,
    }): CancelablePromise<Array<Concept>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/valueset/{codeSetNm}/concepts',
            path: {
                'codeSetNm': codeSetNm,
            },
        });
    }
    /**
     * @returns Concept OK
     * @throws ApiError
     */
    public static createConcept({
        codeSetNm,
        requestBody,
    }: {
        codeSetNm: string,
        requestBody: CreateConceptRequest,
    }): CancelablePromise<Concept> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/valueset/{codeSetNm}/concepts',
            path: {
                'codeSetNm': codeSetNm,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns PageConcept OK
     * @throws ApiError
     */
    public static searchConcepts({
        codeSetNm,
        page,
        size = 25,
        sort,
    }: {
        codeSetNm: string,
        /**
         * Zero-based page index (0..N)
         */
        page?: number,
        /**
         * The size of the page to be returned
         */
        size?: number,
        /**
         * Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
         */
        sort?: Array<string>,
    }): CancelablePromise<PageConcept> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/valueset/{codeSetNm}/concepts/search',
            path: {
                'codeSetNm': codeSetNm,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
        });
    }
}
