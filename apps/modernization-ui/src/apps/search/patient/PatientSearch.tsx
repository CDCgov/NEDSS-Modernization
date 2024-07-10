import { useEffect } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { PatientSearchResult } from 'generated/graphql/schema';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { usePage } from 'page';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { usePatientSearch } from './usePatientSearch';
import { PatientCriteriaEntry, initial } from './criteria';
import { PatientSearchResultListItem } from './result/list';
import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { PatientSearchResultTable } from './result/table';

const PatientSearch = () => {
    const methods = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const {
        page: { total }
    } = usePage();

    const { status, search, reset, results } = usePatientSearch();

    useEffect(() => {
        if (status === 'waiting') {
            methods.reset();
        }
    }, [methods.reset, status]);

    return (
        <FormProvider {...methods}>
            <SearchLayout
                actions={() => (
                    <ButtonActionMenu
                        label="Add new"
                        items={[
                            { label: 'Add new patient', action: () => {} },
                            { label: 'Add new lab report', action: () => {} }
                        ]}
                        disabled={total === 0}
                    />
                )}
                criteria={() => <PatientCriteria />}
                resultsAsList={() => (
                    <SearchResultList<PatientSearchResult>
                        results={results?.content ?? []}
                        render={(result) => <PatientSearchResultListItem result={result} />}
                    />
                )}
                resultsAsTable={() => <PatientSearchResultTable results={results?.content ?? []} />}
                onSearch={methods.handleSubmit(search)}
                onClear={reset}
            />
        </FormProvider>
    );
};

export { PatientSearch };
