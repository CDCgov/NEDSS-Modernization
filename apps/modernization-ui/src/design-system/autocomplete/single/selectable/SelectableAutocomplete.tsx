import { AutocompleteSingleProps, Autocomplete } from 'design-system/autocomplete';
import { AutocompleteOptionsResolver } from 'options/autocompete';
import { Selectable } from 'options/selectable';

type SelectableAutocompleteSingleProps = AutocompleteSingleProps<Selectable>;
type Props = SelectableAutocompleteSingleProps & { resolver: AutocompleteOptionsResolver };

const SelectableAutocomplete = ({ ...props }: Props) => {
    return <Autocomplete<Selectable> {...props} asValue={(value) => value} asText={(value) => value?.name} />;
};

export { SelectableAutocomplete };
export type { SelectableAutocompleteSingleProps };
