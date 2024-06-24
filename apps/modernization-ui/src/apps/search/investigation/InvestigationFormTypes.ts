import { Selectable } from 'components/FormInputs/SelectInput';
import { EventId, InvestigationStatus, ProviderFacilitySearch } from 'generated/graphql/schema';

export type InvestigationFilterEntry = {
    createdBy?: Selectable;
    lastUpdatedBy?: Selectable;
    investigatorId?: Selectable;
    pregnancyStatus?: Selectable;
    eventId?: EventId;
    investigationStatus?: InvestigationStatus;
    patientId?: number | null;
    providerFacilitySearch?: ProviderFacilitySearch;
    jurisdictions?: Selectable[];
    conditions?: Selectable[];
    caseStatuses?: Selectable[];
    notificationStatues?: Selectable[];
    outbreakNames?: Selectable[];
    processingStatus?: Selectable;
    programAreas?: Selectable[];
};
