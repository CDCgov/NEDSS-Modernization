/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CaseStatuses } from './CaseStatuses';
import type { InvestigationEventDateSearch } from './InvestigationEventDateSearch';
import type { NotificationStatuses } from './NotificationStatuses';
import type { ProcessingStatuses } from './ProcessingStatuses';
import type { ProviderFacilitySearch } from './ProviderFacilitySearch';

export type InvestigationFilter = {
    caseStatuses?: CaseStatuses;
    conditions?: Array<string>;
    createdBy?: number;
    eventDateSearch?: InvestigationEventDateSearch;
    eventId?: string;
    eventIdType?: InvestigationFilter.eventIdType;
    investigationStatus?: InvestigationFilter.investigationStatus;
    investigatorId?: number;
    jurisdictions?: Array<number>;
    lastUpdatedBy?: number;
    notificationStatuses?: NotificationStatuses;
    outbreakNames?: Array<string>;
    patientId?: number;
    pregnancyStatus?: InvestigationFilter.pregnancyStatus;
    processingStatuses?: ProcessingStatuses;
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

