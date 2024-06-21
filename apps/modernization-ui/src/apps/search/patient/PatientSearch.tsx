import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { PatientSearchResult } from 'generated/graphql/schema';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { useSearch } from 'apps/search';
import { usePatientSearch } from './usePatientSearch';
import { PatientCriteriaEntry, initial } from './criteria';
import { PatientSearchResultListItem } from './result/list';

const PatientSearch = () => {
    const { handleSubmit, reset: resetForm } = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const {
        results: { total }
    } = useSearch();

    const { status, search, reset, results } = usePatientSearch();

    useEffect(() => {
        if (status === 'waiting') {
            resetForm();
        }
    }, [resetForm, status]);

    return (
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
            criteria={() => <div>criteria</div>}
            resultsAsList={() => (
                <SearchResultList<PatientSearchResult>
                    results={results?.content ?? []}
                    render={(result) => <PatientSearchResultListItem result={result} />}
                />
            )}
            resultsAsTable={() => <div>result table</div>}
            onSearch={handleSubmit(search)}
            onClear={reset}
        />
    );
};

export { PatientSearch };
