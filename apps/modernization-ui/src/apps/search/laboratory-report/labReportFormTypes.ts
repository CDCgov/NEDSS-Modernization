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

type ReportCriteria = {
    codedResult?: string;
    resultedTest?: string;
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
};

type LabReportFilterEntry = General & ReportCriteria;

export type { LabReportFilterEntry, General, ReportCriteria, Identification, EventDate };

const ELECTRONIC_ENTRY = asSelectable('ELECTRONIC', 'Electronic');

const entryMethodTypes: Selectable[] = [ELECTRONIC_ENTRY, asSelectable('MANUAL', 'Manual')];

const enteredByTypes: Selectable[] = [asSelectable('EXTERNAL', 'External'), asSelectable('INTERNAL', 'Internal')];

const NEW_STATUS = asSelectable('NEW', 'New');

const eventStatusTypes: Selectable[] = [NEW_STATUS, asSelectable('UPDATE', 'Update')];

const identificationTypes: Selectable[] = [
    asSelectable('ACCESSION_NUMBER', 'Accession Number'),
    asSelectable('LAB_ID', 'Lab Id')
];

const UNPROCESSED = asSelectable('UNPROCESSED', 'Unprocessed');

const processingStatusTypes: Selectable[] = [UNPROCESSED, asSelectable('PROCESSED', 'Processed')];

const dateTypes: Selectable[] = [
    asSelectable('DATE_OF_REPORT', 'Date Of Report'),
    asSelectable('DATE_OF_SPECIMEN_COLLECTION', 'Date of Specimen Collection'),
    asSelectable('DATE_RECEIVED_BY_PUBLIC_HEALTH', 'Date Received By Public Health'),
    asSelectable('LAB_REPORT_CREATE_DATE', 'Lab Report Create Date'),
    asSelectable('LAST_UPDATE_DATE', 'Last Update Date')
];

const entityTypes: Selectable[] = [
    asSelectable('ORDERING_FACILITY', 'Ordering Facility'),
    asSelectable('ORDERING_PROVIDER', 'Ordering Provider'),
    asSelectable('REPORTING_FACILITY', 'Reporting Facility')
];

const initial: LabReportFilterEntry = {
    enteredBy: enteredByTypes,
    entryMethods: [ELECTRONIC_ENTRY],
    eventStatus: [NEW_STATUS],
    processingStatus: [UNPROCESSED]
};

// when EventID is provided, the form should be initialized with all fields selected
const initialForEventId: LabReportFilterEntry = {
    enteredBy: enteredByTypes,
    entryMethods: entryMethodTypes,
    eventStatus: eventStatusTypes,
    processingStatus: processingStatusTypes
};

export {
    entityTypes,
    identificationTypes,
    dateTypes,
    enteredByTypes,
    entryMethodTypes,
    eventStatusTypes,
    processingStatusTypes,
    pregnancyStatusOptions as pregnancyStatus,
    initial,
    initialForEventId
};
