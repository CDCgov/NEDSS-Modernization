/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { LaboratoryEventDateSearch } from './LaboratoryEventDateSearch';
import type { LabReportEventId } from './LabReportEventId';
import type { LabReportProviderSearch } from './LabReportProviderSearch';

export type LabReportFilter = {
    codedResult?: string;
    createdBy?: number;
    enteredBy?: Array<'EXTERNAL' | 'INTERNAL'>;
    entryMethods?: Array<'ELECTRONIC' | 'MANUAL'>;
    eventDate?: LaboratoryEventDateSearch;
    eventId?: LabReportEventId;
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

    export enum pregnancyStatus {
        NO = 'NO',
        UNKNOWN = 'UNKNOWN',
        YES = 'YES',
    }


}

