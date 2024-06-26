import { Selectable } from 'components/FormInputs/SelectInput';
import {
    EventId,
    InvestigationEventDateSearch,
    InvestigationStatus,
    ProviderFacilitySearch
} from 'generated/graphql/schema';

export type InvestigationFilterEntry = {
    caseStatuses?: Selectable[];
    conditions?: Selectable[];
    createdBy?: Selectable;
    eventDate?: InvestigationEventDateSearch;
    eventId?: EventId;
    investigationStatus?: InvestigationStatus;
    investigatorId?: Selectable;
    jurisdictions?: Selectable[];
    lastUpdatedBy?: Selectable;
    outbreakNames?: Selectable[];
    notificationStatues?: Selectable[];
    patientId?: number | null;
    pregnancyStatus?: Selectable;
    processingStatus?: Selectable;
    programAreas?: Selectable[];
    providerFacilitySearch?: ProviderFacilitySearch;
};
