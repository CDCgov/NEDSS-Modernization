/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Configuration } from '../models/Configuration';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConfigurationControllerService {

    /**
     * getConfiguration
     * @returns Configuration OK
     * @throws ApiError
     */
    public static getConfigurationUsingGet({
        authorization,
    }: {
        authorization: string,
    }): CancelablePromise<Configuration> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/configuration',
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
