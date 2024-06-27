import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { PatientSearchResult } from 'generated/graphql/schema';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { usePage } from 'page';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { usePatientSearch } from './usePatientSearch';
import { PatientCriteriaEntry, initial } from './criteria';
import { PatientSearchResultListItem } from './result/list';
import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { Selectable } from 'options';

const PatientSearch = () => {
    const {
        control,
        trigger,
        handleSubmit,
        reset: resetForm
    } = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues: initial,
        mode: 'onChange'
    });

    const {
        page: { total }
    } = usePage();

    const { status, search, reset, results } = usePatientSearch();

    useEffect(() => {
        if (status === 'waiting') {
            resetForm();
        }
    }, [resetForm, status]);

    const handleRecordStatusChange = (
        value: Selectable[],
        status: Selectable,
        isChecked: boolean,
        onChange: (status: Selectable[]) => void
    ): void => {
        if (isChecked) {
            value.push(status);
            onChange(value);
        } else {
            const index = value.findIndex((item) => item.value === status.value);
            value.splice(index, 1);
            onChange(value);
        }
        validate();
    };

    const validate = async () => {
        await trigger('status');
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
                    disabled={total === 0}
                />
            )}
            criteria={() => <PatientCriteria control={control} handleRecordStatusChange={handleRecordStatusChange} />}
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
