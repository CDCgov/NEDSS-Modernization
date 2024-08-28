import { Selectable, pregnancyStatusOptions } from 'options';
import { asSelectable } from 'options/selectable';

type EventDate = {
    from: string;
    to: string;
    type: Selectable;
};

type Identification = {
    type: Selectable;
    value: string;
};

type InvestigationFilterEntry = {
    conditions?: Selectable[];
    programAreas?: Selectable[];
    jurisdictions?: Selectable[];
    pregnancyStatus?: Selectable;
    identification?: Identification;
    eventDate?: EventDate;
    createdBy?: Selectable;
    updatedBy?: Selectable;
    investigationStatus?: Selectable;
    investigator?: Selectable;
    outbreaks?: Selectable[];
    caseStatuses?: Selectable[];
    processingStatuses?: Selectable[];
    notificationStatuses?: Selectable[];
    reportingProviderId?: Selectable;
    reportingFacilityId?: Selectable;
};

export type { InvestigationFilterEntry, EventDate, Identification };

//  This should be using PHC_IN_STS however, the API doesn't use value sets
const investigationStatusOptions = [asSelectable('CLOSED', 'Closed'), asSelectable('OPEN', 'Open')];

//  This should be using PHVS_PHC_CLASS however, the API doesn't use value sets
const caseStatusOptions = [
    asSelectable('CONFIRMED', 'Confirmed'),
    asSelectable('NOT_A_CASE', 'Not a case'),
    asSelectable('PROBABLE', 'Probable'),
    asSelectable('SUSPECT', 'Suspect'),
    asSelectable('UNASSIGNED', 'Unassigned'),
    asSelectable('UNKNOWN', 'Unknown')
];

//  This is a subset of the NBS_EVENT_SEARCH_DATES value set
const dateTypeOptions = [
    asSelectable('DATE_OF_REPORT', 'Date of report'),
    asSelectable('INVESTIGATION_CLOSED_DATE', 'Investigation closed date'),
    asSelectable('INVESTIGATION_CREATE_DATE', 'Investigation create date'),
    asSelectable('INVESTIGATION_START_DATE', 'Investigation start date'),
    asSelectable('LAST_UPDATE_DATE', 'Last update date'),
    asSelectable('NOTIFICATION_CREATE_DATE', 'Notification create date')
];

//  This is a subset of the REC_STAT value set
const notificationStatusOptions = [
    asSelectable('APPROVED', 'Approved'),
    asSelectable('COMPLETED', 'Completed'),
    asSelectable('MESSAGE_FAILED', 'Message Failed'),
    asSelectable('PENDING_APPROVAL', 'Pending Approval'),
    asSelectable('REJECTED', 'Rejected'),
    asSelectable('UNASSIGNED', 'Unassigned')
];

//  This should be using CM_PROCESS_STAGE however, the API doesn't use value sets
const processingStatusOptions = [
    asSelectable('AWAITING_INTERVIEW', 'Awaiting Interview'),
    asSelectable('CLOSED_CASE', 'Closed Case'),
    asSelectable('FIELD_FOLLOW_UP', 'Field Follow-up'),
    asSelectable('NO_FOLLOW_UP', 'No Follow-up'),
    asSelectable('OPEN_CASE', 'Open Case'),
    asSelectable('SURVEILLANCE_FOLLOW_UP', 'Surveillance Follow-up'),
    asSelectable('UNASSIGNED', 'Unassigned')
];

const entityOptions: Selectable[] = [
    asSelectable('REPORTING_PROVIDER', 'Reporting Provider'),
    asSelectable('REPORTING_FACILITY', 'Reporting Facility')
];

const investigationEventTypeOptions: Selectable[] = [
    asSelectable('ABCS_CASE_ID', 'ABC Case ID'),
    asSelectable('CITY_COUNTY_CASE_ID', 'City Case ID'),
    asSelectable('INVESTIGATION_ID', 'Investigation ID'),
    asSelectable('NOTIFICATION_ID', 'Notification ID'),
    asSelectable('STATE_CASE_ID', 'State Case ID')
];

export {
    investigationStatusOptions,
    entityOptions,
    notificationStatusOptions,
    processingStatusOptions,
    caseStatusOptions,
    dateTypeOptions,
    investigationEventTypeOptions,
    pregnancyStatusOptions
};
