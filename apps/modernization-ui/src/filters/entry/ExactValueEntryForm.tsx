import { Controller, useFormContext } from 'react-hook-form';

import { AutocompleteMulti } from 'design-system/autocomplete';
import { ValueProperty } from 'filters/properties';
import { Selectable } from 'options';

import { FilterEntry } from './FilterEntry';

const asFilterValue = (selectable: Selectable) => {
    return selectable.name;
};

type ExactValueEntryFormProps = {
    property: ValueProperty;
};

const ExactValueEntryForm = ({ property }: ExactValueEntryFormProps) => {
    const { control } = useFormContext<FilterEntry, Partial<FilterEntry>>();

    return (
        <Controller
            control={control}
            name="values"
            shouldUnregister={true}
            rules={{
                required: { value: true, message: 'At least one value is required.' },
            }}
            render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                <AutocompleteMulti
                    label="Value"
                    name={name}
                    id={name}
                    options={property.all ?? []}
                    resolver={property.complete ?? (() => Promise.resolve([]))}
                    onBlur={onBlur}
                    onChange={onChange}
                    error={error?.message}
                    asValue={asFilterValue}
                    orientation="vertical"
                />
            )}
        />
    );
};

export { ExactValueEntryForm };
