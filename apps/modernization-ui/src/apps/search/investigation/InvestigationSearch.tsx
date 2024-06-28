import { SearchLayout } from 'apps/search/layout';
import InvestigationSearchForm from './InvestigationSearchForm';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { EventId, InvestigationStatus, ProviderFacilitySearch } from 'generated/graphql/schema';

const InvestigationSearch = () => {
    const defaultSelectable = { name: '', value: '', label: '' };

    const defaultValues: InvestigationFilterEntry = {
        createdBy: defaultSelectable,
        lastUpdatedBy: defaultSelectable,
        investigatorId: defaultSelectable,
        pregnancyStatus: defaultSelectable,
        eventId: {} as EventId,
        investigationStatus: {} as InvestigationStatus,
        patientId: null,
        providerFacilitySearch: {} as ProviderFacilitySearch,
        jurisdictions: [defaultSelectable],
        conditions: [defaultSelectable],
        caseStatuses: [defaultSelectable],
        notificationStatues: [defaultSelectable],
        outbreakNames: [defaultSelectable],
        processingStatus: defaultSelectable,
        programAreas: [defaultSelectable]
    };
    const form = useForm<InvestigationFilterEntry>({ defaultValues });

    return (
        <SearchCriteriaProvider>
            <SearchLayout
                criteria={() => <InvestigationSearchForm form={form} />}
                resultsAsList={() => <div>result list</div>}
                resultsAsTable={() => <div>result table</div>}
                onSearch={() => form.handleSubmit}
                onClear={() => form.reset}
            />
        </SearchCriteriaProvider>
    );
};

export { InvestigationSearch };
