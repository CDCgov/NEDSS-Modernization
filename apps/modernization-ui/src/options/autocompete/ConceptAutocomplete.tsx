import { ConceptOptionsService } from 'generated';
import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { AutocompleteOptionsResolver } from './useSelectableAutocomplete';

const resolver =
    (valueSet: string): AutocompleteOptionsResolver =>
    (criteria: string, limit?: number) =>
        ConceptOptionsService.conceptSearch({
            name: valueSet,
            criteria: criteria,
            limit: limit
        }).then((response) => response.options);

type Props = {
    valueSet: string;
} & AutocompleteSingleProps;

const ConceptAutocomplete = ({ valueSet, ...rest }: Props) => <Autocomplete resolver={resolver(valueSet)} {...rest} />;

export { ConceptAutocomplete };
