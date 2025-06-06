/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PatientName } from '../models/PatientName';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientNamesService {
    /**
     * Patient Names
     * Patient Names
     * @returns PatientName OK
     * @throws ApiError
     */
    public static patientNames({
        patientId,
    }: {
        patientId: number,
    }): CancelablePromise<Array<PatientName>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patient/{patientId}/names',
            path: {
                'patientId': patientId,
            },
        });
    }
}
