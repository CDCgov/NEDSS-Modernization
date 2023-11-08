/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddDefault } from '../models/AddDefault';
import type { AddHyperlink } from '../models/AddHyperlink';
import type { AddReadOnlyComments } from '../models/AddReadOnlyComments';
import type { AddStaticResponse } from '../models/AddStaticResponse';
import type { DeleteElementRequest } from '../models/DeleteElementRequest';
import type { DeleteStaticResponse } from '../models/DeleteStaticResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageStaticControllerService {

    /**
     * deleteStaticElement
     * @returns DeleteStaticResponse OK
     * @throws ApiError
     */
    public static deleteStaticElementUsingDelete({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: DeleteElementRequest,
    }): CancelablePromise<DeleteStaticResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/pages/{page}/content/static/delete-static-element',
            path: {
                'page': page,
            },
            headers: {
                'Authorization': authorization,
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }

    /**
     * addStaticHyperLink
     * @returns AddStaticResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static addStaticHyperLinkUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: AddHyperlink,
    }): CancelablePromise<AddStaticResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/content/static/hyperlink',
            path: {
                'page': page,
            },
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

    /**
     * addStaticLineSeparator
     * @returns AddStaticResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static addStaticLineSeparatorUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: AddDefault,
    }): CancelablePromise<AddStaticResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/content/static/line-separator',
            path: {
                'page': page,
            },
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

    /**
     * addStaticOriginalElectronicDocList
     * @returns AddStaticResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static addStaticOriginalElectronicDocListUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: AddDefault,
    }): CancelablePromise<AddStaticResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/content/static/original-elec-doc-list',
            path: {
                'page': page,
            },
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

    /**
     * addStaticReadOnlyComments
     * @returns AddStaticResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static addStaticReadOnlyCommentsUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: AddReadOnlyComments,
    }): CancelablePromise<AddStaticResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/content/static/read-only-comments',
            path: {
                'page': page,
            },
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

    /**
     * addStaticReadOnlyParticipantsList
     * @returns AddStaticResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static addStaticReadOnlyParticipantsListUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: AddDefault,
    }): CancelablePromise<AddStaticResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/content/static/read-only-participants-list',
            path: {
                'page': page,
            },
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
