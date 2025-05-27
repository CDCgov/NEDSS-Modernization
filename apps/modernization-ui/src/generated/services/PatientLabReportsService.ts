/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PatientLabReport } from '../models/PatientLabReport';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientLabReportsService {
    /**
     * Patient Lab Reports
     * Patient Lab Reports
     * @returns PatientLabReport OK
     * @throws ApiError
     */
    public static patientLabReports({
        patientId,
    }: {
        patientId: number,
    }): CancelablePromise<Array<PatientLabReport>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patient/{patientId}/labreports',
            path: {
                'patientId': patientId,
            },
        });
    }
}
