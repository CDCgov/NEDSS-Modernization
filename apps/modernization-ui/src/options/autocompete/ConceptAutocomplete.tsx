import {
    SelectableAutocomplete,
    SelectableAutocompleteSingleProps,
} from 'design-system/autocomplete/single/selectable';
import { ConceptOptionsService } from 'generated';

import { AutocompleteOptionsResolver } from './useSelectableAutocomplete';

const resolver =
    (valueSet: string): AutocompleteOptionsResolver =>
    (criteria: string, limit?: number) =>
        ConceptOptionsService.conceptSearch({
            name: valueSet,
            criteria,
            limit,
        }).then((response) => response.options);

type Props = {
    valueSet: string;
} & SelectableAutocompleteSingleProps;

const ConceptAutocomplete = ({ valueSet, ...rest }: Props) => (
    <SelectableAutocomplete resolver={resolver(valueSet)} {...rest} />
);

export { ConceptAutocomplete };
