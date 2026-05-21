/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class StdWorkerNameOptionsService {
    /**
     * STD HIV Worker Name Option
     * Provides all STD HIV program area worker names.
     * @returns Option OK
     * @throws ApiError
     */
    public static stdHivWorkerNames({
        programAreas,
    }: {
        programAreas?: Array<string>,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/person/stdHivWorker/names',
            query: {
                'programAreas': programAreas,
            },
        });
    }
}
