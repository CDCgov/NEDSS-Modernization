import { FormProvider, useForm } from 'react-hook-form';
import { Investigation } from 'generated/graphql/schema';
import { useConceptOptions } from 'options/concepts';
import { findByValue } from 'options';
import { SearchInteractionProvider } from 'apps/search';
import { sorting } from 'apps/search/basic';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { InvestigationSearchResultListItem } from './result/list';
import { SortingPreferenceProvider } from 'design-system/sorting/preferences';
import { InvestigationSearchResultsTable, preferences } from './result/table';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { InvestigationSearchForm } from './InvestigationSearchForm';
import { useInvestigationSearch } from './useInvestigationSearch';

const InvestigationSearch = () => {
    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'onBlur'
    });

    const interaction = useInvestigationSearch({ form });

    const { options: notificationStatus } = useConceptOptions('REC_STAT', { lazy: false });
    const notificationStatusResolver = findByValue(notificationStatus);

    return (
        <ColumnPreferenceProvider id="search.investigations.preferences.columns" defaults={preferences}>
            <SortingPreferenceProvider id="search.investigations.preferences.sorting" available={sorting}>
                <SearchInteractionProvider interaction={interaction}>
                    <FormProvider {...form}>
                        <SearchLayout
                            criteria={() => <InvestigationSearchForm />}
                            resultsAsList={() => (
                                <SearchResultList<Investigation>
                                    results={interaction.results.content}
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
                                    results={interaction.results.content}
                                    notificationStatusResolver={notificationStatusResolver}
                                />
                            )}
                            searchEnabled={interaction.enabled}
                            onSearch={interaction.search}
                            onClear={interaction.clear}
                        />
                    </FormProvider>
                </SearchInteractionProvider>
            </SortingPreferenceProvider>
        </ColumnPreferenceProvider>
    );
};

export { InvestigationSearch };
