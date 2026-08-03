import { AutocompleteSingleProps, Autocomplete } from 'design-system/autocomplete';
import { AutocompleteOptionsResolver } from 'options/autocompete';
import { Optional } from 'types';

type TextAutocompleteSingleProps = Optional<AutocompleteSingleProps<string>, 'asValue' | 'asText'>;
type Props = TextAutocompleteSingleProps & { resolver: AutocompleteOptionsResolver };

const TextAutocomplete = ({ onChange, ...props }: Props) => {
    return (
        <Autocomplete<string>
            {...props}
            onChange={onChange}
            onEntered={onChange}
            asValue={(suggestion) => suggestion?.name}
            asText={(value) => value}
        />
    );
};

export { TextAutocomplete };
export type { TextAutocompleteSingleProps };
