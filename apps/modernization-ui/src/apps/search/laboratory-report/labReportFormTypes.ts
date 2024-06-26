import { Selectable } from 'options';
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

type ReportCriteria = {
    codedResult?: Selectable;
    resultedTest?: Selectable;
};

type General = {
    createdBy?: Selectable;
    updatedBy?: Selectable;
    enteredBy?: Selectable[];
    entryMethods?: Selectable[];
    eventDate?: EventDate;
    identification?: Identification;
    eventStatus?: Selectable[];
    jurisdictions?: Selectable[];
    patientId?: number;
    entityType?: Selectable;
    orderingFacility?: Selectable;
    orderingProvider?: Selectable;
    reportingFacility?: Selectable;
    pregnancyStatus?: Selectable;
    processingStatus?: Selectable[];
    programAreas?: Selectable[];
} & ReportCriteria;

type LabReportFilterEntry = General & ReportCriteria;

export type { LabReportFilterEntry, General, ReportCriteria, Identification, EventDate };

const entryMethodTypes: Selectable[] = [asSelectable('ELECTRONIC', 'Electronic'), asSelectable('MANUAL', 'Manual')];

const enteredByTypes: Selectable[] = [asSelectable('EXTERNAL', 'External'), asSelectable('INTERNAL', 'Internal')];

const eventStatusTypes: Selectable[] = [asSelectable('NEW', 'New'), asSelectable('UPDATE', 'Update')];

const identificationTypes: Selectable[] = [
    asSelectable('ACCESSION_NUMBER', 'Assecsion Number'),
    asSelectable('LAB_ID', 'Lab Id')
];

//  this should be from the CM_PROCESS_STAGE value set
const processingStatusTypes: Selectable[] = [
    asSelectable('AWAITING_INTERVIEW', 'Awaiting Interview'),
    asSelectable('CLOSED_CASE', 'Closed Case'),
    asSelectable('FIELD_FOLLOW_UP', 'Field Follow Up'),
    asSelectable('NO_FOLLOW_UP', 'No Follow Up'),
    asSelectable('OPEN_CASE', 'Field Follow Up'),
    asSelectable('SURVEILLANCE_FOLLOW_UP', 'Surveillance Follow Up'),
    asSelectable('UNASSIGNED', 'Unassigned')
];

const dateTypes: Selectable[] = [
    asSelectable('DATE_OF_REPORT', 'Date Of Report'),
    asSelectable('DATE_OF_SPECIMEN_COLLECTION', 'Date Of Specification'),
    asSelectable('DATE_RECEIVED_BY_PUBLIC_HEALTH', 'Date Received By Public Health'),
    asSelectable('LAB_REPORT_CREATE_DATE', 'Lab Report Create Date'),
    asSelectable('LAST_UPDATE_DATE', 'Last Update Date')
];

const entityTypes: Selectable[] = [
    asSelectable('ORDERING_FACILITY', 'Ordering Facility'),
    asSelectable('ORDERING_PROVIDER', 'Ordering Provider'),
    asSelectable('REPORTING_FACILITY', 'Reporting Facility')
];

export {
    entityTypes,
    identificationTypes,
    dateTypes,
    enteredByTypes,
    entryMethodTypes,
    eventStatusTypes,
    processingStatusTypes
};
