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

import { useJurisdictionOptions } from 'options/jurisdictions';
import { LaboratoryReportSearchResultsTable, preferences } from './result/table';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';

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
        <ColumnPreferenceProvider id="search.laboratory-reports.preferences.columns" defaults={preferences}>
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
                    resultsAsTable={() => (
                        <LaboratoryReportSearchResultsTable
                            results={results?.content ?? []}
                            jurisdictionResolver={findById}
                        />
                    )}
                    searchEnabled={form.formState.isValid}
                    onSearch={form.handleSubmit(search)}
                    onClear={reset}
                />
            </FormProvider>
        </ColumnPreferenceProvider>
    );
};

export { LaboratoryReportSearch };
