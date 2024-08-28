/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class CodedResultOptionsService {
    /**
     * NBS Coded result Option Autocomplete
     * Provides options from Local or SNOMED coded results that have a name or code matching a criteria.
     * @returns Option OK
     * @throws ApiError
     */
    public static codedResultAutocomplete({
        criteria,
        limit = 15,
    }: {
        criteria: string,
        limit?: number,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/coded-result/search',
            query: {
                'criteria': criteria,
                'limit': limit,
            },
        });
    }
}
