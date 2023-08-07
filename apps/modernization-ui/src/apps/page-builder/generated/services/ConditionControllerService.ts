/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateConditionResponse} from '../models/CreateConditionResponse';
import type { CreateConditionRequest} from '../models/CreateConditionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConditionControllerService {
    /**
     * createCondition
     * @returns CreateConditionResponse Created
     * @throws ApiError
     */
    public static createConditionUsingPost({
        authorization,
        request,
    }: {
        authorization: any,
        /**
         * request
         */
        request: CreateConditionRequest,
    }): CancelablePromise<CreateConditionResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/condition/',
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
