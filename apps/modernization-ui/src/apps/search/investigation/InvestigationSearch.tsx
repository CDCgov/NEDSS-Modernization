import { SearchLayout, SearchResultList } from 'apps/search/layout';
import InvestigationSearchForm from './InvestigationSearchForm';
import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';
import { useEffect } from 'react';
import { Investigation } from 'generated/graphql/schema';
import { InvestigationSearchResultListItem } from './result/list';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { Term, useSearchResultDisplay } from '../useSearchResultDisplay';

const InvestigationSearch = () => {
    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'all'
    });

    const { status, search, reset, results } = useInvestigationSearch();
    const { terms } = useSearchResultDisplay();

    useEffect(() => {
        if (status === 'waiting') {
            form.reset();
        }
    }, [form.reset, status]);

    const handleRemoveTerm = (term: Term) => {
        const formValues = form.getValues();
        const fieldNames = Object.keys(formValues);

        const matchingField = fieldNames.find((fieldName) => fieldName === term.source);
        if (matchingField && terms.length > 1) {
            if (
                matchingField === 'programAreas' ||
                matchingField === 'jurisdictions' ||
                matchingField === 'conditions'
            ) {
                form.setValue(
                    matchingField,
                    form.getValues()?.[matchingField]?.filter((p) => p.value !== term.value) ?? []
                );
            } else {
                form.resetField(matchingField as keyof InvestigationFilterEntry);
            }
            search(form.getValues());
        } else {
            form.reset();
            reset();
        }
    };

    return (
        <SearchCriteriaProvider>
            <FormProvider {...form}>
                <SearchLayout
                    onRemoveTerm={handleRemoveTerm}
                    criteria={() => <InvestigationSearchForm />}
                    resultsAsList={() => (
                        <SearchResultList<Investigation>
                            results={results?.content ?? []}
                            render={(result) => <InvestigationSearchResultListItem result={result} />}
                        />
                    )}
                    resultsAsTable={() => <div>result table</div>}
                    onSearch={form.handleSubmit(search)}
                    onClear={reset}
                />
            </FormProvider>
        </SearchCriteriaProvider>
    );
};

export { InvestigationSearch };
