/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSectionRequest } from '../models/CreateSectionRequest';
import type { CreateSectionResponse } from '../models/CreateSectionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class SectionControllerService {
    /**
     * createSection
     * @returns CreateSectionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createSectionUsingPost({
        authorization,
        pageId,
        request
    }: {
        authorization: any;
        /**
         * pageId
         */
        pageId: string;
        /**
         * request
         */
        request: CreateSectionRequest;
    }): CancelablePromise<CreateSectionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: `/page-builder/api/v1/pages/${pageId}/sections/`,
            path: {
                page: pageId
            },
            headers: {
                Authorization: authorization
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`
            }
        });
    }
}
