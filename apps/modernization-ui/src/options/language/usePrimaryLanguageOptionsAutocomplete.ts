import { PrimaryLanguageOptionsService } from 'generated';

import { AutocompleteOptionsResolver, SelectableAutocompletion, useSelectableAutocomplete } from 'options/autocompete';

type Settings = {
    initialCriteria?: string;
    limit?: number;
};

const usePrimaryLanguageOptionsAutocomplete = (
    settings: Settings = { initialCriteria: '' }
): SelectableAutocompletion => {
    const resolver: AutocompleteOptionsResolver = (criteria: string, limit?: number) =>
        PrimaryLanguageOptionsService.primaryLanguageComplete({
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

export { usePrimaryLanguageOptionsAutocomplete };
