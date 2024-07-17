import { SearchLayout, SearchResultList } from 'apps/search/layout';
import InvestigationSearchForm from './InvestigationSearchForm';
import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';
import { useEffect } from 'react';
import { Investigation } from 'generated/graphql/schema';
import { InvestigationSearchResultListItem } from './result/list';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { NoInputBanner } from '../NoInputBanner';
import { NoResultsBanner } from '../NoResultsBanner';

const InvestigationSearch = () => {
    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'all'
    });

    const { status, search, reset, results } = useInvestigationSearch();

    useEffect(() => {
        if (status === 'waiting') {
            form.reset();
        }
    }, [form.reset, status]);

    return (
        <SearchCriteriaProvider>
            <FormProvider {...form}>
                <SearchLayout
                    criteria={() => <InvestigationSearchForm />}
                    resultsAsList={() => (
                        <SearchResultList<Investigation>
                            results={results?.content ?? []}
                            render={(result) => <InvestigationSearchResultListItem result={result} />}
                        />
                    )}
                    resultsAsTable={() => <div>result table</div>}
                    onSearch={form.handleSubmit(search)}
                    noInputResults={() => <NoInputBanner />}
                    noResults={() => <NoResultsBanner />}
                    onClear={reset}
                />
            </FormProvider>
        </SearchCriteriaProvider>
    );
};

export { InvestigationSearch };
