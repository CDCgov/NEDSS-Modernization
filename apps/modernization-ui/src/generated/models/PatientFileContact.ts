/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssociatedInvestigation } from './AssociatedInvestigation';
import type { NamedContact } from './NamedContact';
export type PatientFileContact = {
    patient: number;
    identifier: number;
    local: string;
    processingDecision?: string;
    referralBasis?: string;
    createdOn: string;
    namedOn?: string;
    named: NamedContact;
    priority?: string;
    disposition?: string;
    associated?: AssociatedInvestigation;
};

