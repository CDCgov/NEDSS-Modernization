import {
    CaseStatus,
    EventId,
    InvestigationEventDateSearch,
    InvestigationEventDateType,
    InvestigationEventIdType,
    InvestigationFilter,
    InvestigationStatus,
    NotificationStatus,
    PregnancyStatus,
    ProcessingStatus
} from 'generated/graphql/schema';
import { EventDate, Identification, InvestigationFilterEntry } from './InvestigationFormTypes';
import { asNumericValues, asValue, asValues } from 'options/selectable';

export const transformObject = (data: InvestigationFilterEntry): InvestigationFilter => {
    const { ...remaining } = data;

    return {
        conditions: remaining.conditions && asValues(remaining.conditions),
        programAreas: remaining.programAreas && asValues(remaining.programAreas),
        jurisdictions: remaining.jurisdictions && asNumericValues(remaining.jurisdictions),
        pregnancyStatus: remaining.pregnancyStatus && (asValue(remaining.pregnancyStatus) as PregnancyStatus),
        eventId: resolveEventId(remaining.identification),
        eventDate: resolveEventDate(remaining.eventDate),
        createdBy: asValue(remaining.createdBy),
        lastUpdatedBy: asValue(remaining.updatedBy),
        reportingProviderId: asValue(remaining.reportingProviderId),
        reportingFacilityId: asValue(remaining.reportingFacilityId),
        investigationStatus:
            remaining.investigationStatus && (asValue(remaining.investigationStatus) as InvestigationStatus),
        investigatorId: asValue(remaining.investigator),
        outbreakNames: remaining.outbreaks && asValues(remaining.outbreaks),
        caseStatuses: remaining.caseStatuses && (asValues(remaining.caseStatuses) as CaseStatus[]),
        processingStatuses:
            remaining.processingStatuses && (asValues(remaining.processingStatuses) as ProcessingStatus[]),
        notificationStatuses:
            remaining.notificationStatuses && (asValues(remaining.notificationStatuses) as NotificationStatus[])
    };
};

const resolveEventDate = (date?: EventDate): InvestigationEventDateSearch | undefined => {
    if (date && date.type && date.type.value) {
        return {
            type: date.type.value as InvestigationEventDateType,
            from: date.from,
            to: date.to
        };
    }

    return undefined;
};

const resolveEventId = (identification?: Identification): EventId | undefined => {
    if (identification && identification.type && identification.type.value) {
        return {
            id: identification.value,
            investigationEventType: identification.type.value as InvestigationEventIdType
        };
    }
    return undefined;
};
