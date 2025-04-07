/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NotificationStatus } from '../models/NotificationStatus';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class NotificationStatusControllerService {
    /**
     * @returns NotificationStatus OK
     * @throws ApiError
     */
    public static findLatestStatus({
        id,
    }: {
        id: string,
    }): CancelablePromise<NotificationStatus> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/investigations/{id}/notifications/transport/status',
            path: {
                'id': id,
            },
        });
    }
}
