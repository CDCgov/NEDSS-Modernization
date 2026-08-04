import { SearchInteractionProvider } from 'apps/search';
import { sorting } from 'apps/search/basic';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { SortingPreferenceProvider } from 'design-system/sorting/preferences';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';
import { LabReport } from 'generated/graphql/schema';
import { useJurisdictionOptions } from 'options/jurisdictions';
import { FormProvider, useForm } from 'react-hook-form';

import { LaboratoryReportSearchCriteria } from './LaboratoryReportSearchCriteria';
import { initial as defaultValues, LabReportFilterEntry } from './labReportFormTypes';
import { LaboratoryReportSearchResultListItem } from './result/list';
import { LaboratoryReportSearchResultsTable, preferences } from './result/table';
import { useLaboratoryReportSearch } from './useLaboratoryReportSearch';

const LaboratoryReportSearch = () => {
    const form = useForm<LabReportFilterEntry, Partial<LabReportFilterEntry>>({
        defaultValues,
        mode: 'onBlur',
    });

    const interaction = useLaboratoryReportSearch({ form });

    const { resolve: findById } = useJurisdictionOptions();

    return (
        <ColumnPreferenceProvider id="search.laboratory-reports.preferences.columns" defaults={preferences}>
            <SortingPreferenceProvider id="search.laboratory-reports.preferences.sorting" available={sorting}>
                <SearchInteractionProvider interaction={interaction}>
                    <FormProvider {...form}>
                        <SearchLayout
                            criteria={() => <LaboratoryReportSearchCriteria />}
                            resultsAsList={() => (
                                <SearchResultList<LabReport>
                                    results={interaction.results.content}
                                    render={(result) => (
                                        <LaboratoryReportSearchResultListItem
                                            result={result}
                                            jurisdictionResolver={findById}
                                        />
                                    )}
                                />
                            )}
                            resultsAsTable={() => (
                                <LaboratoryReportSearchResultsTable
                                    results={interaction.results.content}
                                    jurisdictionResolver={findById}
                                />
                            )}
                            searchEnabled={interaction.enabled}
                            onSearch={interaction.search}
                            onClear={interaction.clear}
                        />
                    </FormProvider>
                </SearchInteractionProvider>
            </SortingPreferenceProvider>
        </ColumnPreferenceProvider>
    );
};

export { LaboratoryReportSearch };
