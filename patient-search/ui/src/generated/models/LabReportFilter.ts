/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { LaboratoryEventDateSearch } from './LaboratoryEventDateSearch';
import type { LabReportProviderSearch } from './LabReportProviderSearch';

export type LabReportFilter = {
    codedResult?: string;
    createdBy?: number;
    enteredBy?: Array<'EXTERNAL' | 'INTERNAL'>;
    entryMethods?: Array<'ELECTRONIC' | 'MANUAL'>;
    eventDateSearch?: LaboratoryEventDateSearch;
    eventId?: string;
    eventIdType?: LabReportFilter.eventIdType;
    eventStatus?: Array<'NEW' | 'UPDATE'>;
    jurisdictions?: Array<number>;
    lastUpdatedBy?: number;
    patientId?: number;
    pregnancyStatus?: LabReportFilter.pregnancyStatus;
    processingStatus?: Array<'PROCESSED' | 'UNPROCESSED'>;
    programAreas?: Array<string>;
    providerSearch?: LabReportProviderSearch;
    resultedTest?: string;
};

export namespace LabReportFilter {

    export enum eventIdType {
        ACCESSION_NUMBER = 'ACCESSION_NUMBER',
        LAB_ID = 'LAB_ID',
    }

    export enum pregnancyStatus {
        NO = 'NO',
        UNKNOWN = 'UNKNOWN',
        YES = 'YES',
    }


}

