/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Page_PageSummary_ } from '../models/Page_PageSummary_';
import type { PageSummaryRequest } from '../models/PageSummaryRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageSummaryService {

    /**
     * Allows paginated searching of Page Summaries with filters.
     * @returns Page_PageSummary_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static search({
        authorization,
        request,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * request
         */
        request: PageSummaryRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_PageSummary_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/search',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
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
