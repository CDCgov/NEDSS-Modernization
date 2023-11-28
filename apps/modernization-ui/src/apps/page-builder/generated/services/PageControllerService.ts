/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageCreateRequest } from '../models/PageCreateRequest';
import type { PageCreateResponse } from '../models/PageCreateResponse';
import type { PageDeleteResponse } from '../models/PageDeleteResponse';
import type { PageStateResponse } from '../models/PageStateResponse';
import type { Resource } from '../models/Resource';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageControllerService {

    /**
     * createPage
     * @returns PageCreateResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createPageUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: PageCreateRequest,
    }): CancelablePromise<PageCreateResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages',
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
     * downloadPageLibrary
     * @returns Resource OK
     * @throws ApiError
     */
    public static downloadPageLibraryUsingGet({
        authorization,
    }: {
        authorization: string,
    }): CancelablePromise<Resource> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/download',
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

    /**
     * downloadPageLibraryPDF
     * @returns string OK
     * @throws ApiError
     */
    public static downloadPageLibraryPdfUsingGet({
        authorization,
    }: {
        authorization: string,
    }): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/downloadPDF',
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

    /**
     * deletePageDraft
     * @returns PageDeleteResponse OK
     * @throws ApiError
     */
    public static deletePageDraftUsingDelete({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<PageDeleteResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/pages/{id}/delete-draft',
            path: {
                'id': id,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }

    /**
     * savePageDraft
     * @returns PageStateResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static savePageDraftUsingPut({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<PageStateResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{id}/draft',
            path: {
                'id': id,
            },
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

    /**
     * downloadPageMetadata
     * @returns Resource OK
     * @throws ApiError
     */
    public static downloadPageMetadataUsingGet({
        authorization,
        waTemplateUid,
    }: {
        authorization: string,
        /**
         * waTemplateUid
         */
        waTemplateUid: number,
    }): CancelablePromise<Resource> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{waTemplateUid}/download-metadata',
            path: {
                'waTemplateUid': waTemplateUid,
            },
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
