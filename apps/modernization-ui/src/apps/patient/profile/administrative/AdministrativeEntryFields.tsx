import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { AdministrativeEntry } from './AdministrativeEntry';
import { Input } from 'components/FormInputs/Input';

export const AdministrativeEntryFields = () => {
    const { control } = useFormContext<AdministrativeEntry>();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Information as of date"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />

            <Controller
                control={control}
                name="comment"
                rules={{
                    required: { value: true, message: 'Comments are required.' },
                    ...maxLengthRule(2000)
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <>
                        <Input
                            label="General comments"
                            type="text"
                            orientation="horizontal"
                            onBlur={onBlur}
                            onChange={onChange}
                            defaultValue={value}
                            name={name}
                            htmlFor={name}
                            id={name}
                            error={error?.message}
                            multiline
                        />
                    </>
                )}
            />
        </section>
    );
};
