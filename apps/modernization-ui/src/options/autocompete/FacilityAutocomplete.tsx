import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { FacilityOptionsService } from 'generated';

const resolver = (criteria: string, limit?: number) =>
    FacilityOptionsService.facilityAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response);

const FacilityAutocomplete = ({ id, label, onChange, required, onBlur, ...rest }: AutocompleteSingleProps) => (
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

export { FacilityAutocomplete };
