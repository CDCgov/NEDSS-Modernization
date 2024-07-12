import { SearchLayout, SearchResultList } from 'apps/search/layout';
import InvestigationSearchForm from './InvestigationSearchForm';
import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';
import { useEffect } from 'react';
import { Investigation } from 'generated/graphql/schema';
import { InvestigationSearchResultListItem } from './result/list';

const defaultSelectable = { name: '', value: '', label: '' };
const defaultValues: InvestigationFilterEntry = {
    createdBy: defaultSelectable,
    updatedBy: defaultSelectable,
    investigator: defaultSelectable,
    pregnancyStatus: defaultSelectable,
    investigationStatus: defaultSelectable,
    jurisdictions: [defaultSelectable],
    conditions: [defaultSelectable],
    caseStatuses: [defaultSelectable],
    notificationStatuses: [defaultSelectable],
    outbreaks: [defaultSelectable],
    processingStatuses: [defaultSelectable],
    programAreas: [],
    reportingFacility: defaultSelectable
};

const InvestigationSearch = () => {
    const { status, search, reset, results } = useInvestigationSearch();

    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'all',
        defaultValues
    });

    useEffect(() => {
        if (status === 'waiting') {
            form.reset();
        }
    }, [form.reset, status]);

    return (
        <SearchLayout
            criteria={() => (
                <FormProvider {...form}>
                    <InvestigationSearchForm />
                </FormProvider>
            )}
            resultsAsList={() => (
                <SearchResultList<Investigation>
                    results={results?.content ?? []}
                    render={(result) => <InvestigationSearchResultListItem result={result} />}
                />
            )}
            resultsAsTable={() => <div>result table</div>}
            onSearch={form.handleSubmit(search)}
            onClear={reset}
        />
    );
};

export { InvestigationSearch };
