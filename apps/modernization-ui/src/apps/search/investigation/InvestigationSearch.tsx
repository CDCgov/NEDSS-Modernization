import { FormProvider, useForm } from 'react-hook-form';
import { useConceptOptions } from 'options/concepts';
import { findByValue } from 'options';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import { Investigation } from 'generated/graphql/schema';
import { SortingPreferenceProvider } from 'design-system/sorting/preferences';
import { sorting } from 'apps/search/basic';
import { InvestigationSearchResultListItem } from './result/list';
import { InvestigationSearchForm } from './InvestigationSearchForm';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';
import { InvestigationSearchResultsTable, preferences } from './result/table';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';
import { removeTerm } from '../terms';

const InvestigationSearch = () => {
    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'onBlur'
    });

    const { enabled, results, search, clear } = useInvestigationSearch({ form });

    const { terms } = useSearchResultDisplay();

    const handleRemoveTerm = removeTerm(form, () => {
        if (terms.length === 1) {
            clear();
        } else {
            search();
        }
    });

    const { options: notificationStatus } = useConceptOptions('REC_STAT', { lazy: false });
    const notificationStatusResolver = findByValue(notificationStatus);

    return (
        <ColumnPreferenceProvider id="search.investigations.preferences.columns" defaults={preferences}>
            <SortingPreferenceProvider id="search.investigations.preferences.sorting" available={sorting}>
                <FormProvider {...form}>
                    <SearchLayout
                        onRemoveTerm={handleRemoveTerm}
                        criteria={() => <InvestigationSearchForm />}
                        resultsAsList={() => (
                            <SearchResultList<Investigation>
                                results={results}
                                render={(result) => (
                                    <InvestigationSearchResultListItem
                                        result={result}
                                        notificationStatusResolver={notificationStatusResolver}
                                    />
                                )}
                            />
                        )}
                        resultsAsTable={() => (
                            <InvestigationSearchResultsTable
                                results={results}
                                notificationStatusResolver={notificationStatusResolver}
                            />
                        )}
                        searchEnabled={enabled}
                        onSearch={search}
                        onClear={clear}
                    />
                </FormProvider>
            </SortingPreferenceProvider>
        </ColumnPreferenceProvider>
    );
};

export { InvestigationSearch };
