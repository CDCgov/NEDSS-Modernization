import {
    SelectableAutocomplete,
    SelectableAutocompleteSingleProps
} from 'design-system/autocomplete/single/selectable';
import { ProviderOptionsService } from 'generated';

const resolver = (criteria: string, limit?: number) =>
    ProviderOptionsService.providerAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response);

const ProviderAutocomplete = ({
    id,
    label,
    onChange,
    required,
    onBlur,
    ...rest
}: SelectableAutocompleteSingleProps) => (
    <SelectableAutocomplete
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
