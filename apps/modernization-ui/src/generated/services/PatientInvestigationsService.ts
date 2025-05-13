/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PatientInvestigation } from '../models/PatientInvestigation';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientInvestigationsService {
    /**
     * Patient Investigations
     * Patient Investigations
     * @returns PatientInvestigation OK
     * @throws ApiError
     */
    public static patientInvestigations({
        patientId,
    }: {
        patientId: number,
    }): CancelablePromise<Array<PatientInvestigation>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patient/{patientId}/investigations',
            path: {
                'patientId': patientId,
            },
        });
    }
}
