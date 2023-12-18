import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { FilterEntry } from './FilterEntry';
import { isAfter } from 'date-fns';
import { internalizeDate } from 'date';
import { useEffect } from 'react';

const DataRangeEntryForm = () => {
    const { control, trigger } = useFormContext<FilterEntry, Partial<FilterEntry>>();

    const before = useWatch({ control, name: 'before' });
    const after = useWatch({ control, name: 'after' });

    useEffect(() => {
        trigger('after');
    }, [before]);

    const isBeforeTo =
        (to: string) =>
        (after: string): string | undefined => {
            if (to && after) {
                const toDate = new Date(to);
                const afterDate = new Date(after);

                if (isAfter(afterDate, toDate)) {
                    return `Cannot be after 'To' date: ${internalizeDate(toDate)}`;
                }
            }
        };

    return (
        <>
            <Controller
                control={control}
                name="after"
                shouldUnregister
                rules={{
                    required: { value: !before, message: 'From date is required when To is not picked.' },
                    validate: isBeforeTo(before)
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        htmlFor={name}
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
                        htmlFor={name}
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
