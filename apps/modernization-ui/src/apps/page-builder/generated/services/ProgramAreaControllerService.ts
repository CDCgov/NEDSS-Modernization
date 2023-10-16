/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProgramArea } from '../models/ProgramArea';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ProgramAreaControllerService {

    /**
     * getProgramAreas
     * @returns ProgramArea OK
     * @throws ApiError
     */
    public static getProgramAreasUsingGet({
        authorization,
    }: {
        authorization: any,
    }): CancelablePromise<Array<ProgramArea>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/program-area',
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
