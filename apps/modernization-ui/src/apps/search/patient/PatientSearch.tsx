import { useEffect } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { PatientSearchResult } from 'generated/graphql/schema';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { usePatientSearch } from './usePatientSearch';
import { PatientCriteriaEntry, initial } from './criteria';
import { PatientSearchResultListItem } from './result/list';
import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { NoPatientResults } from './result/none';
import { PatientSearchResultTable } from './result/table';
import { Term, useSearchResultDisplay } from '../useSearchResultDisplay';

import { PatientSearchActions } from './PatientSearchActions';

const PatientSearch = () => {
    const methods = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { status, search, reset, results } = usePatientSearch();
    const { terms } = useSearchResultDisplay();

    useEffect(() => {
        if (status === 'resetting') {
            methods.reset();
        }
    }, [methods.reset, status]);

    const handleRemoveTerm = (term: Term) => {
        const formValues = methods.getValues();
        const fieldNames = Object.keys(formValues);

        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            methods.resetField(matchingField as keyof PatientCriteriaEntry);
            search(methods.getValues());
        } else {
            reset();
        }
    };

    return (
        <FormProvider {...methods}>
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
                onSearch={methods.handleSubmit(search)}
                noResults={() => <NoPatientResults />}
                onClear={reset}
            />
        </FormProvider>
    );
};

export { PatientSearch };
