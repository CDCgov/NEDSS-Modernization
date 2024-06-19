import { useForm } from 'react-hook-form';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { SearchLayout } from 'apps/search/layout';
import { useSearch } from 'apps/search';
import { usePatientSearch } from './usePatientSearch';
import { PatientCriteriaEntry, initial } from './criteria';
import { useEffect } from 'react';

const PatientSearch = () => {
    const { handleSubmit, reset: resetForm } = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { reset: resetSearch, complete, search, results } = useSearch();

    const patientSearch = usePatientSearch();

    useEffect(() => {
        if (patientSearch.status === 'loading') {
            search();
        } else if (patientSearch.status === 'completed') {
            complete([], patientSearch.results?.total || 0);
        }
    }, [patientSearch.status, patientSearch.results?.total || 0, search, complete]);

    const handleSearch = (criteria: PatientCriteriaEntry) => {
        patientSearch.search(criteria);
    };

    const handleClear = () => {
        resetForm();
        resetSearch();
    };

    return (
        <SearchLayout
            actions={() => (
                <ButtonActionMenu
                    label="Add new"
                    items={[
                        { label: 'Add new patient', action: () => {} },
                        { label: 'Add new lab report', action: () => {} }
                    ]}
                    disabled={results.total === 0}
                />
            )}
            criteria={() => <div>criteria</div>}
            resultsAsList={() => <div>result list</div>}
            resultsAsTable={() => <div>result table</div>}
            onSearch={() => {
                handleSubmit(handleSearch);
            }}
            onClear={handleClear}
        />
    );
};

export { PatientSearch };
