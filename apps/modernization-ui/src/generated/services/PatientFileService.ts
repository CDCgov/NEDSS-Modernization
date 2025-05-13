/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PatientDemographicsSummary } from '../models/PatientDemographicsSummary';
import type { PatientFile } from '../models/PatientFile';
import type { StandardResponse } from '../models/StandardResponse';
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
    /**
     * Patient File Header
     * @returns PatientFile OK
     * @throws ApiError
     */
    public static file({
        patientId,
    }: {
        patientId: number,
    }): CancelablePromise<PatientFile> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patientId}/file',
            path: {
                'patientId': patientId,
            },
        });
    }
    /**
     * Allows deleting of a patient.
     * @returns StandardResponse The patient has been deleted
     * @throws ApiError
     */
    public static delete({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<StandardResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/api/patients/{patient}',
            path: {
                'patient': patient,
            },
            errors: {
                400: `The patient could not be deleted.`,
            },
        });
    }
}
