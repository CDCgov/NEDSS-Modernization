import { useEffect } from 'react';

import { Controller, useFormContext, useWatch } from 'react-hook-form';

import { DatePickerInput } from 'design-system/date';
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
                shouldUnregister={true}
                rules={{
                    required: { value: !before, message: 'From date is required when To is not picked.' },
                    validate: isBefore(before),
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        label="From"
                        required={!before}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="before"
                shouldUnregister={true}
                rules={{
                    required: {
                        value: !after,
                        message: 'To date is required when From is not picked.',
                    },
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        label="To"
                        required={!after}
                        error={error?.message}
                    />
                )}
            />
        </>
    );
};

export { DataRangeEntryForm };
