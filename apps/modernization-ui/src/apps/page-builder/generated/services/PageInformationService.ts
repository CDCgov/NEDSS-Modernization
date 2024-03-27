/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageInformation } from '../models/PageInformation';
import type { PageInformationChangeRequest } from '../models/PageInformationChangeRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageInformationService {

    /**
     * Returns the Page Information of a page
     * The Page Information includes the event type, message mapping guide, name, datamart, description, and any related conditions
     * @returns PageInformation OK
     * @throws ApiError
     */
    public static find({
        page,
    }: {
        page: number,
    }): CancelablePromise<PageInformation> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{page}/information',
            path: {
                'page': page,
            },
        });
    }

    /**
     * Allows changing the Information of a page
     * Allows changing message mapping guide, name, datamart, description, and related conditions of a Page.
     * @returns any OK
     * @throws ApiError
     */
    public static change({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: PageInformationChangeRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/information',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
