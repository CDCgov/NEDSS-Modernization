/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { InvestigationEventDateSearch } from './InvestigationEventDateSearch';
import type { ProviderFacilitySearch } from './ProviderFacilitySearch';

export type InvestigationFilter = {
    caseStatuses?: Array<'CONFIRMED' | 'NOT_A_CASE' | 'PROBABLE' | 'SUSPECT' | 'UNASSIGNED' | 'UNKNOWN'>;
    conditions?: Array<string>;
    createdBy?: number;
    eventDateSearch?: InvestigationEventDateSearch;
    eventId?: string;
    eventIdType?: InvestigationFilter.eventIdType;
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

    export enum eventIdType {
        ABCS_CASE_ID = 'ABCS_CASE_ID',
        CITY_COUNTY_CASE_ID = 'CITY_COUNTY_CASE_ID',
        INVESTIGATION_ID = 'INVESTIGATION_ID',
        NOTIFICATION_ID = 'NOTIFICATION_ID',
        STATE_CASE_ID = 'STATE_CASE_ID',
    }

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

