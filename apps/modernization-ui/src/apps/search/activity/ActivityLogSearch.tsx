/* eslint-disable */
import { FormProvider, useForm } from 'react-hook-form';
// import { ActivityLog } from './result/elr-result/schema';
import { useConceptOptions } from 'options/concepts';
import { findByValue } from 'options';
import { SearchInteractionProvider } from 'apps/search';
import { sorting } from 'apps/search/basic';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
// import { ElrActivityLogSearchResultListItem } from './result/elr-result/list';
import { SortingPreferenceProvider } from 'design-system/sorting/preferences';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';
import {ActivityFilterEntry, reportType} from './ActivityLogFormTypes';
import { ActivityLogSearchForm } from './ActivityLogSearchForm';
import { useActivityLogSearch } from './useActivityLogSearch';
import {ReactNode, useEffect, useState} from 'react';
import {ElrActivityLogSearchResultsTable} from "./result/elr-result/table";
import {Direction} from "../../../sorting";
import {useComponentSizing} from "../../../design-system/sizing";

const ActivityLogSearch = () => {
    const form = useForm<ActivityFilterEntry, Partial<ActivityFilterEntry>>({
        mode: 'onBlur'
    });

    const interaction = useActivityLogSearch({ form });
    const sizing = useComponentSizing();

    return (
        <ColumnPreferenceProvider id="search.activitylog.preferences.columns">
            <SortingPreferenceProvider id="search.activitylog.preferences.sorting"
                                       available={sorting}
                                       defaultSort={{
                                           property: 'activitylog',
                                           direction: Direction.Descending
                                       }}>
                <SearchInteractionProvider interaction={interaction}>
                    <FormProvider {...form}>
                        <SearchLayout
                            sizing={sizing}
                            //actions={() => <PatientSearchActions disabled={interaction.status !== 'completed'} />}
                            criteria={() => <ActivityLogSearchForm sizing={sizing} />}
                            resultsAsTable={() => (
                                <ElrActivityLogSearchResultsTable sizing={sizing}
                                                                  results={interaction.results.content}/>
                            )}
                            searchEnabled={interaction.enabled}
                            onSearch={interaction.search}
                            //noResults={() => <NoPatientResults />}
                            onClear={interaction.clear}
                            resultsAsList={function (): ReactNode {
                                console.log('Function not implemented.');
                                return ;
                            //throw new Error('Function not implemented.');
                            }}
                        />
                    </FormProvider>
                </SearchInteractionProvider>
            </SortingPreferenceProvider>
        </ColumnPreferenceProvider>
    );
};

export { ActivityLogSearch };
