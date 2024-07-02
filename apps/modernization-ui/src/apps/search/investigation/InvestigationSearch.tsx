import { SearchLayout, SearchResultList } from 'apps/search/layout';
import InvestigationSearchForm from './InvestigationSearchForm';
import { useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';
import { useEffect } from 'react';
import { Investigation } from 'generated/graphql/schema';
import { InvestigationSearchResultListItem } from './result/list';

const InvestigationSearch = () => {
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
    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'all',
        defaultValues
    });

    const { status, search, reset, results } = useInvestigationSearch();

    useEffect(() => {
        if (status === 'waiting') {
            form.reset();
        }
    }, [form.reset, status]);

    return (
        <SearchLayout
            criteria={() => <InvestigationSearchForm form={form} />}
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
