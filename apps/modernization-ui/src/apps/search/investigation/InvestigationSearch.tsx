import { useEffect } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { Investigation } from 'generated/graphql/schema';
import { useConceptOptions } from 'options/concepts';
import { findByValue } from 'options';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { Term, useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import { InvestigationSearchResultListItem } from './result/list';
import { InvestigationSearchForm } from './InvestigationSearchForm';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { useInvestigationSearch } from './useInvestigationSearch';

const InvestigationSearch = () => {
    const form = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'onBlur'
    });

    const { status, search, reset, results } = useInvestigationSearch();
    const { terms } = useSearchResultDisplay();

    useEffect(() => {
        if (status === 'resetting') {
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
            reset();
        }
    };

    const { options: notificationStatus } = useConceptOptions('REC_STAT', { lazy: false });

    return (
        <FormProvider {...form}>
            <SearchLayout
                onRemoveTerm={handleRemoveTerm}
                criteria={() => <InvestigationSearchForm />}
                resultsAsList={() => (
                    <SearchResultList<Investigation>
                        results={results?.content ?? []}
                        render={(result) => (
                            <InvestigationSearchResultListItem
                                result={result}
                                notificationStatusResolver={findByValue(notificationStatus)}
                            />
                        )}
                    />
                )}
                resultsAsTable={() => <div>result table</div>}
                searchEnabled={form.formState.isValid}
                onSearch={form.handleSubmit(search)}
                onClear={reset}
            />
        </FormProvider>
    );
};

export { InvestigationSearch };
