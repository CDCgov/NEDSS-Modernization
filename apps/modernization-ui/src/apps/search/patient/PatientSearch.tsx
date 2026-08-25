import { FormProvider, useForm } from 'react-hook-form';

import { SearchInteractionProvider } from 'apps/search';
import { sorting } from 'apps/search/basic';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { PaginationPreferenceProvider } from 'design-system/pagination';
import { useComponentSizing } from 'design-system/sizing';
import { SortingPreferenceProvider } from 'design-system/sorting/preferences';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';
import { PatientSearchResult } from 'generated/graphql/schema';
import { Direction } from 'libs/sorting';

import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { PatientSearchActions } from './PatientSearchActions';
import { initial as defaultValues, PatientCriteriaEntry } from './criteria';
import { PatientSearchResultListItem } from './result/list';
import { NoPatientResults } from './result/none';
import { PatientSearchResultTable, preferences } from './result/table';
import { usePatientSearch } from './usePatientSearch';

const PatientSearch = () => {
    const form = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues,
        mode: 'onBlur',
        reValidateMode: 'onBlur',
    });

    const interaction = usePatientSearch({ form });
    const sizing = useComponentSizing();

    return (
        <ColumnPreferenceProvider id="search.patients.preferences.columns" defaults={preferences}>
            <SortingPreferenceProvider
                id="search.patients.preferences.sorting"
                available={sorting}
                defaultSort={{
                    property: 'patientname',
                    direction: Direction.Ascending,
                }}
            >
                <PaginationPreferenceProvider id="search.patients.preferences.pagination">
                    <SearchInteractionProvider interaction={interaction}>
                        <FormProvider {...form}>
                            <SearchLayout
                                sizing={sizing}
                                actions={() => <PatientSearchActions disabled={interaction.status !== 'completed'} />}
                                criteria={() => <PatientCriteria sizing={sizing} />}
                                resultsAsList={() => (
                                    <SearchResultList<PatientSearchResult>
                                        results={interaction.results.content}
                                        render={(result) => <PatientSearchResultListItem result={result} />}
                                    />
                                )}
                                resultsAsTable={() => (
                                    <PatientSearchResultTable sizing={sizing} results={interaction.results.content} />
                                )}
                                searchEnabled={interaction.enabled}
                                onSearch={interaction.search}
                                noResults={() => <NoPatientResults />}
                                onClear={interaction.clear}
                            />
                        </FormProvider>
                    </SearchInteractionProvider>
                </PaginationPreferenceProvider>
            </SortingPreferenceProvider>
        </ColumnPreferenceProvider>
    );
};

export { PatientSearch };
