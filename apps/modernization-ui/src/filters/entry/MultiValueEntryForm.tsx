import { Controller, useFormContext } from 'react-hook-form';
import { MultiSelectAutocomplete } from 'components/selection/multi';
import { FilterEntry } from './FilterEntry';
import { ValueProperty } from 'filters/properties';
import { Selectable } from 'options';

const asFilterValue = (selectable: Selectable) => {
    return selectable.name;
};

type MultiValueEntryFormProps = {
    property: ValueProperty;
};

const MultiValueEntryForm = ({ property }: MultiValueEntryFormProps) => {
    const { control } = useFormContext<FilterEntry, Partial<FilterEntry>>();

    return (
        <Controller
            control={control}
            name="values"
            shouldUnregister
            rules={{
                required: { value: true, message: 'At least one value is required.' }
            }}
            render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                <MultiSelectAutocomplete
                    label="Value"
                    name={name}
                    id={name}
                    options={property.all}
                    complete={property.complete}
                    onBlur={onBlur}
                    onChange={onChange}
                    error={error?.message}
                    asValue={asFilterValue}
                />
            )}
        />
    );
};

export { MultiValueEntryForm };
