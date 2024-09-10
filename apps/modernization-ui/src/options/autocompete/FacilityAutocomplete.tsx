import {
    SelectableAutocomplete,
    SelectableAutocompleteSingleProps
} from 'design-system/autocomplete/single/selectable';
import { FacilityOptionsService } from 'generated';

const resolver = (criteria: string, limit?: number) =>
    FacilityOptionsService.facilityAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response);

const FacilityAutocomplete = ({
    id,
    label,
    onChange,
    value,
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
        value={value}
        {...rest}
    />
);

export { FacilityAutocomplete };
