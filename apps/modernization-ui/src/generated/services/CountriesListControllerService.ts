/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class CountriesListControllerService {
    /**
     * @returns Option OK
     * @throws ApiError
     */
    public static addressTypes(): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/countries',
        });
    }
}
