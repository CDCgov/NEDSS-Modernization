import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { LabReport } from 'generated/graphql/schema';
import { useLaboratoryReportSearch } from './useLaboratoryReportSearch';
import { LabReportFilterEntry, initial } from './labReportFormTypes';
import { LaboratoryReportSearchResultListItem } from './result/list';
import { FormAccordion } from './FormAccordion';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { Term } from '../terms';
import { useSearchResultDisplay } from '../useSearchResultDisplay';

const LaboratoryReportSearch = () => {
    const formMethods = useForm<LabReportFilterEntry, Partial<LabReportFilterEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { status, search, reset, results } = useLaboratoryReportSearch();
    const { terms } = useSearchResultDisplay();

    useEffect(() => {
        if (status === 'waiting') {
            formMethods.reset();
        }
    }, [formMethods.reset, status]);

    const handleRemoveTerm = (term: Term) => {
        const formValues = formMethods.getValues();
        const fieldNames = Object.keys(formValues);
        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            if (matchingField === 'programAreas' || matchingField === 'jurisdictions') {
                formMethods.setValue(
                    matchingField,
                    formMethods.getValues()?.[matchingField]?.filter((p) => p.value !== term.value) ?? []
                );
            } else {
                formMethods.resetField(matchingField as keyof LabReportFilterEntry);
            }
            search(formMethods.getValues());
        } else {
            formMethods.reset();
            reset();
        }
    };

    return (
        <SearchCriteriaProvider>
            <SearchLayout
                onRemoveTerm={handleRemoveTerm}
                criteria={() => <FormAccordion form={formMethods} />}
                resultsAsList={() => (
                    <SearchResultList<LabReport>
                        results={results?.content || []}
                        render={(result) => <LaboratoryReportSearchResultListItem result={result} />}
                    />
                )}
                resultsAsTable={() => <div>result table</div>}
                onSearch={formMethods.handleSubmit(search)}
                onClear={reset}
            />
        </SearchCriteriaProvider>
    );
};

export { LaboratoryReportSearch };
