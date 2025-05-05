/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PatientDemographicsSummary } from '../models/PatientDemographicsSummary';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientFileService {
    /**
     * Patient File Demographics Summary
     * Provides summarized demographics of a patient
     * @returns PatientDemographicsSummary OK
     * @throws ApiError
     */
    public static summary({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<PatientDemographicsSummary> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics',
            path: {
                'patient': patient,
            },
        });
    }
}
