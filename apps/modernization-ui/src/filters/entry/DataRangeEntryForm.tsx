import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { isBefore } from 'validation/date';
import { FilterEntry } from './FilterEntry';

const DataRangeEntryForm = () => {
    const { control, trigger } = useFormContext<FilterEntry, Partial<FilterEntry>>();

    const before = useWatch({ control, name: 'before' });
    const after = useWatch({ control, name: 'after' });

    useEffect(() => {
        if (before) {
            trigger('after');
        }
    }, [before]);

    return (
        <>
            <Controller
                control={control}
                name="after"
                shouldUnregister
                rules={{
                    required: { value: !before, message: 'From date is required when To is not picked.' },
                    validate: isBefore(before)
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        label="From"
                        disableFutureDates
                        required={!before}
                        errorMessage={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="before"
                shouldUnregister
                rules={{
                    required: {
                        value: !after,
                        message: 'To date is required when From is not picked.'
                    }
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        label="To"
                        disableFutureDates
                        required={!after}
                        errorMessage={error?.message}
                    />
                )}
            />
        </>
    );
};

export { DataRangeEntryForm };
