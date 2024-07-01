import { SearchLayout, SearchResultList } from 'apps/search/layout';
import InvestigationSearchForm from './InvestigationSearchForm';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { EventId, InvestigationStatus, ProviderFacilitySearch } from 'generated/graphql/schema';
import { useInvestigationSearch } from './useInvestigationSearch';
import { useEffect } from 'react';
import { Investigation } from 'generated/graphql/schema';
import { InvestigationSearchResultListItem } from './result/list';

const InvestigationSearch = () => {
    const defaultSelectable = { name: '', value: '', label: '' };
    const { handleSubmit, reset: resetForm } = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'onBlur'
    });

    const { status, search, reset, results } = useInvestigationSearch();

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

    useEffect(() => {
        if (status === 'waiting') {
            resetForm();
        }
    }, [resetForm, status]);
    const form = useForm<InvestigationFilterEntry>({ defaultValues });

    return (
        <SearchCriteriaProvider>
            <SearchLayout
                criteria={() => <InvestigationSearchForm form={form} />}
                resultsAsList={() => (
                    <SearchResultList<Investigation>
                        results={results?.content ?? []}
                        render={(result) => <InvestigationSearchResultListItem result={result} />}
                    />
                )}
                resultsAsTable={() => <div>result table</div>}
                onSearch={handleSubmit(search)}
                onClear={reset}
            />
        </SearchCriteriaProvider>
    );
};

export { InvestigationSearch };
