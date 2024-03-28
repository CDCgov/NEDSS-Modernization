/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProgramArea } from '../models/ProgramArea';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ProgramAreaControllerService {
    /**
     * @returns ProgramArea OK
     * @throws ApiError
     */
    public static getProgramAreas(): CancelablePromise<Array<ProgramArea>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/program-area',
        });
    }
}
