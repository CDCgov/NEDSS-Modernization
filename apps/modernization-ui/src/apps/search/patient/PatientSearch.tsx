import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { PatientSearchResult } from 'generated/graphql/schema';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { usePatientSearch } from './usePatientSearch';
import { PatientCriteriaEntry, initial } from './criteria';
import { PatientSearchResultListItem } from './result/list';
import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { NoPatientResults } from './result/none';
import { PatientSearchResultTable } from './result/table';
import { Term, useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';

import { PatientSearchActions } from './PatientSearchActions';

const PatientSearch = () => {
    const form = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { status, search, reset, results } = usePatientSearch();
    const { terms } = useSearchResultDisplay();

    const { state } = useLocation();

    useEffect(() => {
        form.reset(state, { keepDefaultValues: true });
        search({ ...initial, ...state });
    }, [state, form.reset]);

    useEffect(() => {
        if (status === 'resetting') {
            form.reset();
        }
    }, [form.reset, status]);

    const handleRemoveTerm = (term: Term) => {
        const formValues = form.getValues();
        const fieldNames = Object.keys(formValues);

        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            form.resetField(matchingField as keyof PatientCriteriaEntry);
            search(form.getValues());
        } else {
            reset();
        }
    };

    return (
        <FormProvider {...form}>
            <SearchLayout
                onRemoveTerm={handleRemoveTerm}
                actions={() => <PatientSearchActions />}
                criteria={() => <PatientCriteria />}
                resultsAsList={() => (
                    <SearchResultList<PatientSearchResult>
                        results={results?.content ?? []}
                        render={(result) => <PatientSearchResultListItem result={result} />}
                    />
                )}
                resultsAsTable={() => <PatientSearchResultTable results={results?.content ?? []} />}
                searchEnabled={form.formState.isValid}
                onSearch={form.handleSubmit(search)}
                noResults={() => <NoPatientResults />}
                onClear={reset}
            />
        </FormProvider>
    );
};

export { PatientSearch };
