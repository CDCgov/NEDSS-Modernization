/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PagePublishRequest } from '../models/PagePublishRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PagePublishControllerService {
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static publishPage({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: PagePublishRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{id}/publish',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
