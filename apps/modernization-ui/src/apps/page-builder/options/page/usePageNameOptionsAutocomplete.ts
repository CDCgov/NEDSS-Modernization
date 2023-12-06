import { PageBuilderOptionsService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import {
    AutocompleteOptionsResolver,
    PageBuilderOptionsAutocompletion,
    usePageBuilderOptionsAutocomplete
} from 'apps/page-builder/options/usePageBuilderOptionsAutocomplete';

type Settings = {
    initialCriteria?: string;
    limit?: number;
};

const usePageNameOptionsAutocomplete = (
    settings: Settings = { initialCriteria: '' }
): PageBuilderOptionsAutocompletion => {
    const resolver: AutocompleteOptionsResolver = (criteria: string, limit?: number) =>
        PageBuilderOptionsService.pageNamesAutocomplete({
            authorization: authorization(),
            criteria,
            limit
        });

    const { criteria, options, suggest, complete, reset } = usePageBuilderOptionsAutocomplete({
        resolver,
        criteria: settings.initialCriteria,
        limit: settings.limit
    });

    return {
        criteria,
        options,
        suggest,
        complete,
        reset
    };
};

export { usePageNameOptionsAutocomplete };
