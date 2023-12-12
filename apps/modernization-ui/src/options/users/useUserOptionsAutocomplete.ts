import { UserOptionsService } from 'generated';

import { AutocompleteOptionsResolver, SelectableAutocompletion, useSelectableAutocomplete } from 'options/autocompete';

type Settings = {
    initialCriteria?: string;
    limit?: number;
};

const useUserOptionsAutocomplete = (settings: Settings = { initialCriteria: '' }): SelectableAutocompletion => {
    const resolver: AutocompleteOptionsResolver = (criteria: string, limit?: number) =>
        UserOptionsService.userAutocomplete({
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

export { useUserOptionsAutocomplete };
