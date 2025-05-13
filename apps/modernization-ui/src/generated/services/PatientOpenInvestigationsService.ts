/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PatientInvestigation } from '../models/PatientInvestigation';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientOpenInvestigationsService {
    /**
     * Patient Open Investigations
     * Patient Open Investigations
     * @returns PatientInvestigation OK
     * @throws ApiError
     */
    public static patientOpenInvestigations({
        patientId,
    }: {
        patientId: number,
    }): CancelablePromise<Array<PatientInvestigation>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patient/{patientId}/investigations/open',
            path: {
                'patientId': patientId,
            },
        });
    }
}
