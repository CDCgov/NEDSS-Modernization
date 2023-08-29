/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSubSectionRequest } from '../models/CreateSubSectionRequest';
import type { CreateSubSectionResponse } from '../models/CreateSubSectionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class SubSectionControllerService {
    /**
     * createSubSection
     * @returns CreateSubSectionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createSubSectionUsingPost({
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
        request: CreateSubSectionRequest;
    }): CancelablePromise<CreateSubSectionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: `/page-builder/api/v1/pages/${pageId}/subsections/`,
            path: {
                page: page
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
