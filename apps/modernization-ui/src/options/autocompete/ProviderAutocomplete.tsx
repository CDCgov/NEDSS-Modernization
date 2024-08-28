import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { ProviderOptionsService } from 'generated';

const resolver = (criteria: string, limit?: number) =>
    ProviderOptionsService.providerAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response);

const ProviderAutocomplete = ({ id, label, onChange, required, onBlur, ...rest }: AutocompleteSingleProps) => (
    <Autocomplete
        resolver={resolver}
        onChange={onChange}
        required={required}
        onBlur={onBlur}
        id={id}
        label={label}
        {...rest}
    />
);

export { ProviderAutocomplete };
