import { LaboratoryEventDateSearch, LabReportEventId, LabReportProviderSearch } from 'generated/graphql/schema';
import { Selectable } from 'options';

export type LabReportFilterEntry = {
    codedResult?: Selectable;
    createdBy?: Selectable;
    enteredBy?: Selectable[];
    entryMethods?: Selectable[];
    eventDate?: LaboratoryEventDateSearch | null;
    eventId?: LabReportEventId | null;
    eventStatus?: Selectable[];
    jurisdictions?: Selectable[];
    lastUpdatedBy?: Selectable;
    patientId?: number | null;
    providerSearch?: LabReportProviderSearch | null;
    pregnancyStatus?: Selectable;
    processingStatus?: Selectable[];
    programAreas?: Selectable[];
    resultedTest?: Selectable;
};
