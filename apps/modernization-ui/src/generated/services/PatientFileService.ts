/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Administrative } from '../models/Administrative';
import type { DocumentRequiringReview } from '../models/DocumentRequiringReview';
import type { PatientAddressDemographic } from '../models/PatientAddressDemographic';
import type { PatientDemographicsSummary } from '../models/PatientDemographicsSummary';
import type { PatientFile } from '../models/PatientFile';
import type { PatientIdentificationDemographic } from '../models/PatientIdentificationDemographic';
import type { PatientInvestigation } from '../models/PatientInvestigation';
import type { PatientPhoneDemographic } from '../models/PatientPhoneDemographic';
import type { Success } from '../models/Success';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientFileService {
    /**
     * Patient File Documents Requiring Review
     * Provides Documents Requiring Review for a patient
     * @returns DocumentRequiringReview OK
     * @throws ApiError
     */
    public static documentsRequiringReview({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<DocumentRequiringReview>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/documents-requiring-review',
            path: {
                'patient': patient,
            },
        });
    }
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
     * Patient File Phone Demographics
     * Provides the phone demographics for a patient
     * @returns PatientPhoneDemographic OK
     * @throws ApiError
     */
    public static phones({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientPhoneDemographic>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/phones',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Identification Demographics
     * Provides the identification demographics for a patient
     * @returns PatientIdentificationDemographic OK
     * @throws ApiError
     */
    public static identifications({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientIdentificationDemographic>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/identifications',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Administrative Information
     * Provides the administrative information for a patient
     * @returns Administrative OK
     * @throws ApiError
     */
    public static administrative({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Administrative> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/administrative',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Address Demographics
     * Provides the address demographics for a patient
     * @returns PatientAddressDemographic OK
     * @throws ApiError
     */
    public static phones1({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientAddressDemographic>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/addresses',
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
     * Patient Investigations
     * Patient Investigations
     * @returns PatientInvestigation OK
     * @throws ApiError
     */
    public static investigations({
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
    /**
     * Patient Open Investigations
     * Patient Open Investigations
     * @returns PatientInvestigation OK
     * @throws ApiError
     */
    public static openInvestigations({
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
    /**
     * Allows deleting of a patient.
     * @returns Success The patient has been deleted
     * @throws ApiError
     */
    public static delete({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Success> {
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
