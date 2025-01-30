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
import { useFilter } from 'design-system/filter/useFilter';
import { useEffect } from 'react';

const PatientSearch = () => {
    const form = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues,
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    const interaction = usePatientSearch({ form });
    const { filterEntry, onReset } = useFilter();

    const handleSearch = () => {
        form.setValue('filter', undefined);
        onReset();
        interaction.search();
    };

    useEffect(() => {
        if (filterEntry) {
            form.setValue('filter', filterEntry);
            interaction.search();
        }
    }, [filterEntry]);

    return (
        <ColumnPreferenceProvider id="search.patients.preferences.columns" defaults={preferences}>
            <SortingPreferenceProvider
                id="search.patients.preferences.sorting"
                available={sorting}
                defaultSort={{
                    property: 'patientname',
                    direction: Direction.Descending
                }}>
                <SearchInteractionProvider interaction={interaction}>
                    <FormProvider {...form}>
                        <SearchLayout
                            actions={() => <PatientSearchActions disabled={interaction.status !== 'completed'} />}
                            criteria={() => <PatientCriteria />}
                            resultsAsList={() => (
                                <SearchResultList<PatientSearchResult>
                                    results={interaction.results.content}
                                    render={(result) => <PatientSearchResultListItem result={result} />}
                                />
                            )}
                            resultsAsTable={() => <PatientSearchResultTable results={interaction.results.content} />}
                            searchEnabled={interaction.enabled}
                            onSearch={handleSearch}
                            noResults={() => <NoPatientResults />}
                            onClear={interaction.clear}
                        />
                    </FormProvider>
                </SearchInteractionProvider>
            </SortingPreferenceProvider>
        </ColumnPreferenceProvider>
    );
};

export { PatientSearch };
