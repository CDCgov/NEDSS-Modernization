/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { EventDate } from './EventDate';
import type { EventId0 } from './EventId0';
import type { ProviderFacilitySearch } from './ProviderFacilitySearch';

export type InvestigationFilter = {
    caseStatuses?: Array<'CONFIRMED' | 'NOT_A_CASE' | 'PROBABLE' | 'SUSPECT' | 'UNASSIGNED' | 'UNKNOWN'>;
    conditions?: Array<string>;
    createdBy?: number;
    eventDate?: EventDate;
    eventId?: EventId0;
    investigationStatus?: InvestigationFilter.investigationStatus;
    investigatorId?: number;
    jurisdictions?: Array<number>;
    lastUpdatedBy?: number;
    notificationStatuses?: Array<'APPROVED' | 'COMPLETED' | 'MESSAGE_FAILED' | 'PENDING_APPROVAL' | 'REJECTED' | 'UNASSIGNED'>;
    outbreakNames?: Array<string>;
    patientId?: number;
    pregnancyStatus?: InvestigationFilter.pregnancyStatus;
    processingStatuses?: Array<'AWAITING_INTERVIEW' | 'CLOSED_CASE' | 'FIELD_FOLLOW_UP' | 'NO_FOLLOW_UP' | 'OPEN_CASE' | 'SURVEILLANCE_FOLLOW_UP' | 'UNASSIGNED'>;
    programAreas?: Array<string>;
    providerFacilitySearch?: ProviderFacilitySearch;
};

export namespace InvestigationFilter {

    export enum investigationStatus {
        CLOSED = 'CLOSED',
        OPEN = 'OPEN',
    }

    export enum pregnancyStatus {
        NO = 'NO',
        UNKNOWN = 'UNKNOWN',
        YES = 'YES',
    }


}

