import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { LabReport } from 'generated/graphql/schema';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { Term } from 'apps/search/terms';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import { useLaboratoryReportSearch } from './useLaboratoryReportSearch';
import { LabReportFilterEntry, initial } from './labReportFormTypes';
import { LaboratoryReportSearchResultListItem } from './result/list';
import { LaboratoryReportSearchCriteria } from './LaboratoryReportSearchCriteria';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { useJurisdictionOptions } from 'options/jurisdictions';

const LaboratoryReportSearch = () => {
    const form = useForm<LabReportFilterEntry, Partial<LabReportFilterEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { status, search, reset, results } = useLaboratoryReportSearch();

    const { state } = useLocation();

    useEffect(() => {
        if (state) {
            form.reset(state, { keepDefaultValues: true });
            search(state as LabReportFilterEntry);
        }
    }, [state, form.reset]);

    useEffect(() => {
        if (status === 'resetting') {
            form.reset();
        }
    }, [form.reset, status]);

    const { terms } = useSearchResultDisplay();

    const handleRemoveTerm = (term: Term) => {
        const formValues = form.getValues();
        const fieldNames = Object.keys(formValues);
        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            if (matchingField === 'programAreas' || matchingField === 'jurisdictions') {
                form.setValue(
                    matchingField,
                    form.getValues()?.[matchingField]?.filter((p) => p.value !== term.value) ?? []
                );
            } else {
                form.resetField(matchingField as keyof LabReportFilterEntry);
            }
            search(form.getValues());
        } else {
            reset();
        }
    };

    const { resolve: findById } = useJurisdictionOptions();

    return (
        <SearchCriteriaProvider>
            <FormProvider {...form}>
                <SearchLayout
                    onRemoveTerm={handleRemoveTerm}
                    criteria={() => <LaboratoryReportSearchCriteria />}
                    resultsAsList={() => (
                        <SearchResultList<LabReport>
                            results={results?.content || []}
                            render={(result) => (
                                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={findById} />
                            )}
                        />
                    )}
                    resultsAsTable={() => <div>result table</div>}
                    searchEnabled={form.formState.isValid}
                    onSearch={form.handleSubmit(search)}
                    onClear={reset}
                />
            </FormProvider>
        </SearchCriteriaProvider>
    );
};

export { LaboratoryReportSearch };
