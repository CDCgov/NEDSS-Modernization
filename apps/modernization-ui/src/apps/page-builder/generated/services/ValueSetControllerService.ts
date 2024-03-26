/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { County } from '../models/County';
import type { CreateValuesetRequest } from '../models/CreateValuesetRequest';
import type { Pageable } from '../models/Pageable';
import type { PageValueSetOption } from '../models/PageValueSetOption';
import type { UpdateValueSetRequest } from '../models/UpdateValueSetRequest';
import type { Valueset } from '../models/Valueset';
import type { ValueSetOption } from '../models/ValueSetOption';
import type { ValueSetSearchRequest } from '../models/ValueSetSearchRequest';
import type { ValueSetStateChangeResponse } from '../models/ValueSetStateChangeResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ValueSetControllerService {

    /**
     * @returns Valueset OK
     * @throws ApiError
     */
    public static getValueset({
        codeSetNm,
    }: {
        codeSetNm: string,
    }): CancelablePromise<Valueset> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/valueset/{codeSetNm}',
            path: {
                'codeSetNm': codeSetNm,
            },
        });
    }

    /**
     * @returns Valueset OK
     * @throws ApiError
     */
    public static updateValueSet({
        codeSetNm,
        requestBody,
    }: {
        codeSetNm: string,
        requestBody: UpdateValueSetRequest,
    }): CancelablePromise<Valueset> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/valueset/{codeSetNm}',
            path: {
                'codeSetNm': codeSetNm,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns ValueSetStateChangeResponse OK
     * @throws ApiError
     */
    public static deleteValueSet({
        codeSetNm,
    }: {
        codeSetNm: string,
    }): CancelablePromise<ValueSetStateChangeResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/valueset/{codeSetNm}/deactivate',
            path: {
                'codeSetNm': codeSetNm,
            },
        });
    }

    /**
     * @returns ValueSetStateChangeResponse OK
     * @throws ApiError
     */
    public static activateValueSet({
        codeSetNm,
    }: {
        codeSetNm: string,
    }): CancelablePromise<ValueSetStateChangeResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/valueset/{codeSetNm}/activate',
            path: {
                'codeSetNm': codeSetNm,
            },
        });
    }

    /**
     * @returns Valueset OK
     * @throws ApiError
     */
    public static create({
        requestBody,
    }: {
        requestBody: CreateValuesetRequest,
    }): CancelablePromise<Valueset> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/valueset',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns PageValueSetOption OK
     * @throws ApiError
     */
    public static searchValueSet({
        requestBody,
    }: {
        requestBody: {
            pageable?: Pageable;
            request?: ValueSetSearchRequest;
        },
    }): CancelablePromise<PageValueSetOption> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/valueset/options/search',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns County OK
     * @throws ApiError
     */
    public static findCountyByStateCode({
        stateCode,
    }: {
        stateCode: string,
    }): CancelablePromise<Array<County>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/valueset/{stateCode}/counties',
            path: {
                'stateCode': stateCode,
            },
        });
    }

    /**
     * @returns ValueSetOption OK
     * @throws ApiError
     */
    public static findValueSetOptions(): CancelablePromise<Array<ValueSetOption>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/valueset/options',
        });
    }

}
