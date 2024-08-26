import { FormProvider, useForm } from 'react-hook-form';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { Term } from 'apps/search/terms';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import { LabReport } from 'generated/graphql/schema';
import { useLaboratoryReportSearch } from './useLaboratoryReportSearch';
import { LabReportFilterEntry, initial as defaultValues } from './labReportFormTypes';
import { LaboratoryReportSearchResultListItem } from './result/list';
import { LaboratoryReportSearchCriteria } from './LaboratoryReportSearchCriteria';

import { useJurisdictionOptions } from 'options/jurisdictions';
import { LaboratoryReportSearchResultsTable, preferences } from './result/table';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';

const LaboratoryReportSearch = () => {
    const form = useForm<LabReportFilterEntry, Partial<LabReportFilterEntry>>({
        defaultValues,
        mode: 'onBlur'
    });

    const { enabled, results, search, clear } = useLaboratoryReportSearch({ form });

    const { terms } = useSearchResultDisplay();

    const handleRemoveTerm = (term: Term) => {
        const formValues = form.getValues();
        const fieldNames = Object.keys(formValues);
        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            if (
                matchingField === 'programAreas' ||
                matchingField === 'jurisdictions' ||
                matchingField === 'enteredBy' ||
                matchingField === 'entryMethods' ||
                matchingField === 'eventStatus' ||
                matchingField === 'processingStatus'
            ) {
                form.setValue(
                    matchingField,
                    form.getValues()?.[matchingField]?.filter((p) => p.value !== term.value) ?? []
                );
            } else {
                form.resetField(matchingField as keyof LabReportFilterEntry);
            }
            search();
        } else {
            clear();
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
                            results={results}
                            render={(result) => (
                                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={findById} />
                            )}
                        />
                    )}
                    resultsAsTable={() => (
                        <LaboratoryReportSearchResultsTable results={results} jurisdictionResolver={findById} />
                    )}
                    searchEnabled={enabled}
                    onSearch={search}
                    onClear={clear}
                />
            </FormProvider>
        </ColumnPreferenceProvider>
    );
};

export { LaboratoryReportSearch };
