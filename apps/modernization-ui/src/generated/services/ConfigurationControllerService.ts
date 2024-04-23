/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Configuration } from '../models/Configuration';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ConfigurationControllerService {
    /**
     * @returns Configuration OK
     * @throws ApiError
     */
    public static getConfiguration(): CancelablePromise<Configuration> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/configuration',
        });
    }
}
