import { FieldValues, UseFormReturn } from 'react-hook-form';
import { SearchResultSettings, useSearchFormAdapter, useSearchResults } from 'apps/search';

type SearchInteraction<R> = {
    results: R[];
    enabled: boolean;
    search: () => void;
    clear: () => void;
};

type Settings<C extends FieldValues, A, R> = { form: UseFormReturn<C> } & SearchResultSettings<C, A, R>;

const useSearch = <C extends object, A extends object, R extends object>({ form, ...settings }: Settings<C, A, R>) => {
    const interaction = useSearchResults({
        defaultValues: settings.defaultValues,
        transformer: settings.transformer,
        resultResolver: settings.resultResolver,
        termResolver: settings.termResolver
    });

    const { search, clear } = useSearchFormAdapter({ form, interaction });

    const results = interaction.results?.content ?? [];
    const enabled = form.formState.isValid;

    return { results, enabled, search, clear };
};

export { useSearch };
export type { SearchInteraction };
