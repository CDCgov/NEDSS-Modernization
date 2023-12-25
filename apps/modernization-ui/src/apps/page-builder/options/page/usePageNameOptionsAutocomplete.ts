import { PageBuilderOptionsService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';

import { AutocompleteOptionsResolver, SelectableAutocompletion, useSelectableAutocomplete } from 'options/autocompete';

type Settings = {
    initialCriteria?: string;
    limit?: number;
};

const usePageNameOptionsAutocomplete = (settings: Settings = { initialCriteria: '' }): SelectableAutocompletion => {
    const resolver: AutocompleteOptionsResolver = (criteria: string, limit?: number) =>
        PageBuilderOptionsService.pageNamesAutocomplete({
            authorization: authorization(),
            criteria,
            limit
        });

    const { criteria, options, suggest, complete, reset } = useSelectableAutocomplete({
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
