import { FormProvider, useForm } from 'react-hook-form';
import { PatientSearchResult } from 'generated/graphql/schema';
import { SearchInteractionProvider } from 'apps/search';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { sorting } from 'apps/search/basic';
import { SortingPreferenceProvider } from 'design-system/sorting/preferences';
import { PatientSearchResultListItem } from './result/list';
import { NoPatientResults } from './result/none';
import { PatientSearchResultTable, preferences } from './result/table';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';
import { PatientCriteriaEntry, initial as defaultValues } from './criteria';
import { PatientSearchActions } from './PatientSearchActions';
import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { usePatientSearch } from './usePatientSearch';
import { Direction } from 'sorting';
import { useComponentSizing } from 'design-system/sizing';

const PatientSearch = () => {
    const form = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues,
        mode: 'onBlur',
        reValidateMode: 'onBlur'
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
                    direction: Direction.Ascending
                }}>
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
                            noResults={() => <NoPatientResults sizing={sizing} />}
                            onClear={interaction.clear}
                        />
                    </FormProvider>
                </SearchInteractionProvider>
            </SortingPreferenceProvider>
        </ColumnPreferenceProvider>
    );
};

export { PatientSearch };
