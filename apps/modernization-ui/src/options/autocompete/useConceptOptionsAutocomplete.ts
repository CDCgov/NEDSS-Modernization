import { authorization } from 'authorization';
import {
    AutocompleteOptionsResolver,
    SelectableAutocompletion,
    useSelectableAutocomplete
} from './useSelectableAutocomplete';
import { ConceptOptionsService } from 'generated';

type Settings = {
    initialCriteria?: string;
    limit?: number;
};

const useConceptOptionsAutocomplete = (
    valueSet: string,
    settings: Settings = { initialCriteria: '' }
): SelectableAutocompletion => {
    const resolver: AutocompleteOptionsResolver = (criteria: string, limit?: number) =>
        ConceptOptionsService.specificUsingGet({
            authorization: authorization(),
            name: valueSet,
            criteria: criteria,
            limit: limit
        }).then((response) => response.options);

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

export { useConceptOptionsAutocomplete };
