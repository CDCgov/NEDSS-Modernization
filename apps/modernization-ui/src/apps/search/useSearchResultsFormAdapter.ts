import { useCallback, useEffect } from 'react';
import { FieldValues, UseFormReturn } from 'react-hook-form';
import { SearchInteraction, SearchResultSettings, useSearchResults } from 'apps/search';
import { removeTerm, Term } from 'apps/search/terms';

type Settings<C extends FieldValues, A, R> = { form: UseFormReturn<C> } & SearchResultSettings<C, A, R>;

const useSearchResultsFormAdapter = <C extends object, A extends object, R extends object>({
    form,
    ...settings
}: Settings<C, A, R>): SearchInteraction<R> => {
    const interaction = useSearchResults({
        defaultValues: settings.defaultValues,
        transformer: settings.transformer,
        resultResolver: settings.resultResolver,
        termResolver: settings.termResolver
    });

    useEffect(() => {
        if (interaction.status === 'resetting') {
            form.reset();
        } else if (interaction.status === 'initializing') {
            form.reset(interaction.criteria, { keepDefaultValues: true });
        }
    }, [form.reset, interaction.status, interaction.criteria]);

    const status = interaction.status;
    const results = interaction.results;
    const search = useCallback(form.handleSubmit(interaction.search), [form.handleSubmit, interaction.search]);
    const clear = useCallback(interaction.reset, [interaction.reset]);

    const without = useCallback(
        (term: Term) => {
            if (results.terms.length > 1) {
                //  remove the single term and search again
                removeTerm(form, search)(term);
            } else {
                //  there is only one term, clear the search
                clear();
            }
        },
        [search, clear, results.terms.length]
    );

    const enabled = form.formState.isValid;

    return { status, results, enabled, search, without, clear };
};

export { useSearchResultsFormAdapter };
