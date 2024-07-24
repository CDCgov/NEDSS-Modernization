import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { PatientSearchResult } from 'generated/graphql/schema';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { usePatientSearch } from './usePatientSearch';
import { PatientCriteriaEntry, initial } from './criteria';
import { PatientSearchResultListItem } from './result/list';
import { PatientCriteria } from './PatientCriteria/PatientCriteria';
import { NoPatientResultsBanner } from '../NoPatientResultsBanner';
import { PatientSearchResultTable } from './result/table';
import { NoInputBanner } from '../NoInputBanner';
import { Term, useSearchResultDisplay } from '../useSearchResultDisplay';
import { Button } from '@trussworks/react-uswds';

const PatientSearch = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const methods = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { status, search, reset, results } = usePatientSearch();
    const { terms } = useSearchResultDisplay();

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

    const handleRemoveTerm = (term: Term) => {
        const formValues = methods.getValues();
        const fieldNames = Object.keys(formValues);

        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            methods.resetField(matchingField as keyof PatientCriteriaEntry);
            search(methods.getValues());
        } else {
            methods.reset();
            reset();
        }
    };

    return (
        <FormProvider {...methods}>
            <SearchLayout
                onRemoveTerm={handleRemoveTerm}
                actions={() => (
                    <ButtonActionMenu label="Add new" disabled={terms.length === 0}>
                        <>
                            <Button type="button" onClick={handleAddNewPatientClick}>
                                Add new patient
                            </Button>
                            <Button type="button" onClick={handleAddNewLabReportClick}>
                                Add new lab report
                            </Button>
                        </>
                    </ButtonActionMenu>
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
                noInputResults={() => <NoInputBanner />}
                noResults={() => <NoPatientResultsBanner />}
                onClear={reset}
            />
        </FormProvider>
    );
};

export { PatientSearch };
