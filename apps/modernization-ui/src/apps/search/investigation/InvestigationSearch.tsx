import { FormProvider, useForm } from 'react-hook-form';
import { useConceptOptions } from 'options/concepts';
import { findByValue } from 'options';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { Term, useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import { Investigation } from 'generated/graphql/schema';
import { InvestigationSearchResultListItem } from './result/list';
import { InvestigationSearchForm } from './InvestigationSearchForm';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';
import { InvestigationSearchResultsTable, preferences } from './result/table';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';

const InvestigationSearch = () => {
    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'onBlur'
    });

    const { enabled, results, search, clear } = useInvestigationSearch({ form });

    const { terms } = useSearchResultDisplay();

    const handleRemoveTerm = (term: Term) => {
        const formValues = form.getValues();
        const fieldNames = Object.keys(formValues);

        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            if (
                matchingField === 'programAreas' ||
                matchingField === 'jurisdictions' ||
                matchingField === 'conditions' ||
                matchingField === 'notificationStatuses' ||
                matchingField === 'outbreaks' ||
                matchingField === 'caseStatuses' ||
                matchingField === 'processingStatuses'
            ) {
                form.setValue(
                    matchingField,
                    form.getValues()?.[matchingField]?.filter((p) => p.value !== term.value) ?? []
                );
            } else {
                form.resetField(matchingField as keyof InvestigationFilterEntry);
            }
            search();
        } else {
            clear();
        }
    };

    const { options: notificationStatus } = useConceptOptions('REC_STAT', { lazy: false });
    const notificationStatusResolver = findByValue(notificationStatus);

    return (
        <ColumnPreferenceProvider id="search.investigations.preferences.columns" defaults={preferences}>
            <FormProvider {...form}>
                <SearchLayout
                    onRemoveTerm={handleRemoveTerm}
                    criteria={() => <InvestigationSearchForm />}
                    resultsAsList={() => (
                        <SearchResultList<Investigation>
                            results={results}
                            render={(result) => (
                                <InvestigationSearchResultListItem
                                    result={result}
                                    notificationStatusResolver={notificationStatusResolver}
                                />
                            )}
                        />
                    )}
                    resultsAsTable={() => (
                        <InvestigationSearchResultsTable
                            results={results}
                            notificationStatusResolver={notificationStatusResolver}
                        />
                    )}
                    searchEnabled={enabled}
                    onSearch={search}
                    onClear={clear}
                />
            </FormProvider>
        </ColumnPreferenceProvider>
    );
};

export { InvestigationSearch };
