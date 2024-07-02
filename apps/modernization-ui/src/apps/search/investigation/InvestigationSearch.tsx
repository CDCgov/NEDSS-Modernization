import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Investigation } from 'generated/graphql/schema';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { InvestigationSearchResultListItem } from './result/list';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';

const InvestigationSearch = () => {
    const { handleSubmit, reset: resetForm } = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'onBlur'
    });

    const { status, search, reset, results } = useInvestigationSearch();

    useEffect(() => {
        if (status === 'waiting') {
            resetForm();
        }
    }, [resetForm, status]);

    return (
        <SearchLayout
            criteria={() => <div>criteria</div>}
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
    );
};

export { InvestigationSearch };
