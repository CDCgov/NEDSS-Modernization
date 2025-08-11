/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Administrative } from '../models/Administrative';
import type { DocumentRequiringReview } from '../models/DocumentRequiringReview';
import type { EditedPatient } from '../models/EditedPatient';
import type { ExistingRaceCategoryInvalid } from '../models/ExistingRaceCategoryInvalid';
import type { ExistingRaceCategoryValid } from '../models/ExistingRaceCategoryValid';
import type { Failure } from '../models/Failure';
import type { PatientAddressDemographic } from '../models/PatientAddressDemographic';
import type { PatientDemographicsSummary } from '../models/PatientDemographicsSummary';
import type { PatientEthnicityDemographic } from '../models/PatientEthnicityDemographic';
import type { PatientFile } from '../models/PatientFile';
import type { PatientFileContacts } from '../models/PatientFileContacts';
import type { PatientFileDocument } from '../models/PatientFileDocument';
import type { PatientFileTreatment } from '../models/PatientFileTreatment';
import type { PatientGeneralInformationDemographic } from '../models/PatientGeneralInformationDemographic';
import type { PatientIdentificationDemographic } from '../models/PatientIdentificationDemographic';
import type { PatientInvestigation } from '../models/PatientInvestigation';
import type { PatientLabReport } from '../models/PatientLabReport';
import type { PatientMergeHistory } from '../models/PatientMergeHistory';
import type { PatientMorbidityReport } from '../models/PatientMorbidityReport';
import type { PatientMortalityDemographic } from '../models/PatientMortalityDemographic';
import type { PatientNameDemographic } from '../models/PatientNameDemographic';
import type { PatientPhoneDemographic } from '../models/PatientPhoneDemographic';
import type { PatientRaceDemographic } from '../models/PatientRaceDemographic';
import type { PatientSexBirthDemographic } from '../models/PatientSexBirthDemographic';
import type { PatientVaccination } from '../models/PatientVaccination';
import type { Success } from '../models/Success';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
import {MergeStatus} from "../../apps/patient/file/summary/mergeHistory/model/MergeStatus";
export class PatientFileService {
    /**
     * Patient File Header
     * @returns any OK
     * @throws ApiError
     */
    public static edit({
        patient,
        requestBody,
    }: {
        patient: number,
        requestBody: EditedPatient,
    }): CancelablePromise<(Failure | Success)> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/api/patients/{patient}',
            path: {
                'patient': patient,
            },
            body: requestBody,
            mediaType: 'application/json',
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
    /**
     * Validates that a patient can accept a race demographic for the given category.
     * @returns any Allowable race category for the patient
     * @throws ApiError
     */
    public static validateRace({
        patient,
        category,
    }: {
        patient: number,
        category: string,
    }): CancelablePromise<(ExistingRaceCategoryInvalid | ExistingRaceCategoryValid)> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/patients/{patient}/demographics/races/{category}/validate',
            path: {
                'patient': patient,
                'category': category,
            },
            errors: {
                400: `The race category is already present on the patient`,
            },
        });
    }
    /**
     * Patient File Vaccinations
     * Provides Vaccinations for a patient
     * @returns PatientVaccination OK
     * @throws ApiError
     */
    public static vaccinations({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientVaccination>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/vaccinations',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Treatments
     * Provides Treatments for a patient
     * @returns PatientFileTreatment OK
     * @throws ApiError
     */
    public static treatments({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientFileTreatment>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/treatments',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Morbidity Reports
     * Provides Morbidity Reports for a patient
     * @returns PatientMorbidityReport OK
     * @throws ApiError
     */
    public static morbidityReports({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientMorbidityReport>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/reports/morbidity',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Laboratory Reports
     * Provides Documents Requiring Review for a patient
     * @returns PatientLabReport OK
     * @throws ApiError
     */
    public static laboratoryReports({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientLabReport>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/reports/laboratory',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Merge History
     * Provides the merge history for a patient
     * @returns PatientMergeHistory OK
     * @throws ApiError
     */
    public static mergeHistory({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientMergeHistory>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/merge/history',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Documents
     * Provides Documents for a patient
     * @returns PatientFileDocument OK
     * @throws ApiError
     */
    public static documents({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientFileDocument>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/documents',
            path: {
                'patient': patient,
            },
        });
    }
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
     * Patient File Sex & Birth Demographics
     * Provides the Sex & Birth demographics for a patient
     * @returns PatientSexBirthDemographic OK
     * @throws ApiError
     */
    public static sexBirthDemographics({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<PatientSexBirthDemographic> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/sex-birth',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient file race demographics
     * Provides the race demographics for a patient
     * @returns PatientRaceDemographic OK
     * @throws ApiError
     */
    public static raceDemographics({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientRaceDemographic>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/races',
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
     * Patient File Name Demographics
     * Provides the name demographics for a patient
     * @returns PatientNameDemographic OK
     * @throws ApiError
     */
    public static names({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientNameDemographic>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/names',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Mortality Demographics
     * Provides the Mortality demographics for a patient
     * @returns PatientMortalityDemographic OK
     * @throws ApiError
     */
    public static mortalityDemographics({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<PatientMortalityDemographic> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/mortality',
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
     * Patient File General Information Demographics
     * Provides the General Information demographics for a patient
     * @returns PatientGeneralInformationDemographic OK
     * @throws ApiError
     */
    public static generalInformationDemographics({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<PatientGeneralInformationDemographic> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/general',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient file ethnicity demographics
     * Provides the ethnicity demographics for a patient
     * @returns PatientEthnicityDemographic OK
     * @throws ApiError
     */
    public static ethnicityDemographics({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<PatientEthnicityDemographic> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/demographics/ethnicity',
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
    public static addresses({
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
     * Patient File Contacts named by patient
     * Provides contacts that were named by a patient during an investigation
     * @returns PatientFileContacts OK
     * @throws ApiError
     */
    public static contactsNamedByPatient({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientFileContacts>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/contacts',
            path: {
                'patient': patient,
            },
        });
    }
    /**
     * Patient File Contacts that named patient
     * Provides contacts that named the patient during their investigation
     * @returns PatientFileContacts OK
     * @throws ApiError
     */
    public static patientNamedByContact({
        patient,
    }: {
        patient: number,
    }): CancelablePromise<Array<PatientFileContacts>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/patients/{patient}/contacts/named',
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
}
