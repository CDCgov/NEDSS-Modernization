import { FormProvider, useForm } from 'react-hook-form';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { Term, useSearchResultDisplay } from 'apps/search';
import { PatientSearchResult } from 'generated/graphql/schema';
import { usePatientSearch } from './usePatientSearch';
import { PatientSearchResultListItem } from './result/list';
import { NoPatientResults } from './result/none';
import { PatientSearchResultTable, preferences } from './result/table';
import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { PatientCriteriaEntry, initial as defaultValues } from './criteria';

import { PatientSearchActions } from './PatientSearchActions';

const PatientSearch = () => {
    const form = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues,
        mode: 'onBlur'
    });

    const { enabled, results, search, clear } = usePatientSearch({ form });

    const { terms } = useSearchResultDisplay();

    const handleRemoveTerm = (term: Term) => {
        const formValues = form.getValues();
        const fieldNames = Object.keys(formValues);

        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            form.resetField(matchingField as keyof PatientCriteriaEntry);
            search();
        } else {
            clear();
        }
    };

    return (
        <ColumnPreferenceProvider id="search.patients.preferences.columns" defaults={preferences}>
            <FormProvider {...form}>
                <SearchLayout
                    onRemoveTerm={handleRemoveTerm}
                    actions={() => <PatientSearchActions />}
                    criteria={() => <PatientCriteria />}
                    resultsAsList={() => (
                        <SearchResultList<PatientSearchResult>
                            results={results}
                            render={(result) => <PatientSearchResultListItem result={result} />}
                        />
                    )}
                    resultsAsTable={() => <PatientSearchResultTable results={results} />}
                    searchEnabled={enabled}
                    onSearch={search}
                    noResults={() => <NoPatientResults />}
                    onClear={clear}
                />
            </FormProvider>
        </ColumnPreferenceProvider>
    );
};

export { PatientSearch };
