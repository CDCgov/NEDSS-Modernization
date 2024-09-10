import { FormProvider, useForm } from 'react-hook-form';
import { SearchInteractionProvider } from 'apps/search';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { LabReport } from 'generated/graphql/schema';
import { SortingPreferenceProvider } from 'design-system/sorting/preferences';
import { sorting } from 'apps/search/basic';
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
