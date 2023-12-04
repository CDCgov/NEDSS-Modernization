import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { FilterEntry } from './FilterEntry';

const SingleValueEntryForm = () => {
    const { control } = useFormContext<FilterEntry, Partial<FilterEntry>>();

    return (
        <Controller
            control={control}
            name="value"
            shouldUnregister
            rules={{
                required: { value: true, message: 'A value is required.' }
            }}
            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                <Input
                    type="text"
                    label="Value"
                    name={name}
                    htmlFor={name}
                    id={name}
                    defaultValue={value}
                    onBlur={onBlur}
                    onChange={onChange}
                    error={error?.message}
                />
            )}
        />
    );
};

export { SingleValueEntryForm };
