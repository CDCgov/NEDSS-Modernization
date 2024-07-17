import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
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
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
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

    function handleAddNewPatientClick(): void {
        const criteria = searchParams.get('q');

        if (criteria) {
            navigate('/add-patient', { state: { criteria } });
        } else {
            navigate('/add-patient');
        }
    }

    function handleAddNewLabReportClick(): void {
        window.location.href = `/nbs/MyTaskList1.do?ContextAction=AddLabDataEntry`;
    }

    return (
        <FormProvider {...methods}>
            <SearchLayout
                actions={() => (
                    <ButtonActionMenu
                        label="Add new"
                        items={[
                            { label: 'Add new patient', action: handleAddNewPatientClick },
                            { label: 'Add new lab report', action: handleAddNewLabReportClick }
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
