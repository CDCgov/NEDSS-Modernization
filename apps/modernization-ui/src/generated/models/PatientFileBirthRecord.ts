/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { MotherInformation } from './MotherInformation';
export type PatientFileBirthRecord = {
    patient: number;
    id: number;
    local: string;
    receivedOn?: string;
    facility?: string;
    collectedOn?: string;
    certificate?: string;
    mother?: MotherInformation;
    associations?: Array<AssociatedInvestigation>;
};

