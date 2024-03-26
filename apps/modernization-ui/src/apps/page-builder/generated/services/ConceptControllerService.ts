/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Concept } from '../models/Concept';
import type { CreateConceptRequest } from '../models/CreateConceptRequest';
import type { Pageable } from '../models/Pageable';
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
        requestBody,
    }: {
        codeSetNm: string,
        requestBody?: Pageable,
    }): CancelablePromise<PageConcept> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/valueset/{codeSetNm}/concepts/search',
            path: {
                'codeSetNm': codeSetNm,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
