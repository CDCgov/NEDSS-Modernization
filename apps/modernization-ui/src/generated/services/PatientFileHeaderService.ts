/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PatientFileHeader } from '../models/PatientFileHeader';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientFileHeaderService {
    /**
     * Patient File Header
     * Patient File Header
     * @returns PatientFileHeader OK
     * @throws ApiError
     */
    public static patientFileHeader({
        patientId,
    }: {
        patientId: number,
    }): CancelablePromise<PatientFileHeader> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patient/{patientId}/file',
            path: {
                'patientId': patientId,
            },
        });
    }
}
