import { useCallback, useEffect } from 'react';
import { FieldValues, UseFormReturn } from 'react-hook-form';
import { SearchInteraction, SearchResultSettings, useSearchResults } from 'apps/search';
import { removeTerm, Term } from 'apps/search/terms';

type Settings<C extends FieldValues, A, R> = { form: UseFormReturn<C> } & SearchResultSettings<C, A, R>;

/**
 * A custom React hook that adapts search result interactions to work with react-hook-form.
 *
 * This hook bridges the form state management from `react-hook-form` with the search result
 * interaction logic provided by `useSearchResults`. It ensures that form state is synchronized
 * with search status changes (such as resetting or initializing), and provides convenient
 * handlers for searching, clearing, and removing search terms.
 *
 * @template C - The type of the form values.
 * @template A - The type of the search action.
 * @template R - The type of the search result.
 *
 * @param {Settings<C, A, R>} params - The settings object containing the form instance and search result settings.
 * @param {UseFormReturn<C>} params.form - The react-hook-form instance for managing form state.
 * @param {SearchResultSettings<C, A, R>} params - Additional settings for search result interaction.
 *
 * @returns {SearchInteraction<R>} An object containing the search interaction state and handlers:
 * - `status`: The current status of the search interaction.
 * - `results`: The current search results.
 * - `enabled`: Whether the form is valid and search is enabled.
 * - `search`: Handler to submit the search.
 * - `without`: Handler to remove a specific search term and re-execute the search.
 * - `clear`: Handler to clear all search terms and reset the search.
 *
 * @example
 * const { status, results, enabled, search, without, clear } = useSearchResultsFormAdapter({
 *   form,
 *   defaultValues,
 *   transformer,
 *   resultResolver,
 *   termResolver
 * });
 */
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
