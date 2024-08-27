import { FormProvider, useForm } from 'react-hook-form';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { removeTerm, Term } from 'apps/search/terms';
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

    const { resolve: findById } = useJurisdictionOptions();

    return (
        <ColumnPreferenceProvider id="search.laboratory-reports.preferences.columns" defaults={preferences}>
            <FormProvider {...form}>
                <SearchLayout
                    onRemoveTerm={(term: Term) => removeTerm(form, term, search, clear, terms)}
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
