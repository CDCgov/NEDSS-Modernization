/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddDefault } from '../models/AddDefault';
import type { AddHyperlink } from '../models/AddHyperlink';
import type { AddReadOnlyComments } from '../models/AddReadOnlyComments';
import type { AddStaticResponse } from '../models/AddStaticResponse';
import type { DeleteElementRequest } from '../models/DeleteElementRequest';
import type { DeleteStaticResponse } from '../models/DeleteStaticResponse';
import type { UpdateDefault } from '../models/UpdateDefault';
import type { UpdateHyperlink } from '../models/UpdateHyperlink';
import type { UpdateReadOnlyComments } from '../models/UpdateReadOnlyComments';
import type { UpdateStaticResponse } from '../models/UpdateStaticResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageStaticControllerService {

    /**
     * @returns UpdateStaticResponse OK
     * @throws ApiError
     */
    public static updateReadOnlyComments({
        page,
        id,
        requestBody,
    }: {
        page: number,
        id: number,
        requestBody: UpdateReadOnlyComments,
    }): CancelablePromise<UpdateStaticResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/content/static/{id}/read-only-comments',
            path: {
                'page': page,
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns UpdateStaticResponse OK
     * @throws ApiError
     */
    public static updateDefaultStaticElement({
        page,
        id,
        requestBody,
    }: {
        page: number,
        id: number,
        requestBody: UpdateDefault,
    }): CancelablePromise<UpdateStaticResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/content/static/{id}/line-separator',
            path: {
                'page': page,
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns UpdateStaticResponse OK
     * @throws ApiError
     */
    public static updateDefaultStaticElement1({
        page,
        id,
        requestBody,
    }: {
        page: number,
        id: number,
        requestBody: UpdateDefault,
    }): CancelablePromise<UpdateStaticResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/content/static/{id}/participants-list',
            path: {
                'page': page,
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns UpdateStaticResponse OK
     * @throws ApiError
     */
    public static updateDefaultStaticElement2({
        page,
        id,
        requestBody,
    }: {
        page: number,
        id: number,
        requestBody: UpdateDefault,
    }): CancelablePromise<UpdateStaticResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/content/static/{id}/elec-doc-list',
            path: {
                'page': page,
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns UpdateStaticResponse OK
     * @throws ApiError
     */
    public static updateHyperlink({
        page,
        id,
        requestBody,
    }: {
        page: number,
        id: number,
        requestBody: UpdateHyperlink,
    }): CancelablePromise<UpdateStaticResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/content/static/{id}/hyperlink',
            path: {
                'page': page,
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns AddStaticResponse OK
     * @throws ApiError
     */
    public static addStaticReadOnlyParticipantsList({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: AddDefault,
    }): CancelablePromise<AddStaticResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/content/static/read-only-participants-list',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns AddStaticResponse OK
     * @throws ApiError
     */
    public static addStaticReadOnlyComments({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: AddReadOnlyComments,
    }): CancelablePromise<AddStaticResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/content/static/read-only-comments',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns AddStaticResponse OK
     * @throws ApiError
     */
    public static addStaticOriginalElectronicDocList({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: AddDefault,
    }): CancelablePromise<AddStaticResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/content/static/original-elec-doc-list',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns AddStaticResponse OK
     * @throws ApiError
     */
    public static addStaticLineSeparator({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: AddDefault,
    }): CancelablePromise<AddStaticResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/content/static/line-separator',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns AddStaticResponse OK
     * @throws ApiError
     */
    public static addStaticHyperLink({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: AddHyperlink,
    }): CancelablePromise<AddStaticResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/content/static/hyperlink',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns DeleteStaticResponse OK
     * @throws ApiError
     */
    public static deleteStaticElement({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: DeleteElementRequest,
    }): CancelablePromise<DeleteStaticResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/pages/{page}/content/static/delete-static-element',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
