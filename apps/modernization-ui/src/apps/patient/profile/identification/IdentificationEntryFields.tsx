import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { IdentificationEntry } from './identification';
import { usePatientIdentificationCodedValues } from './usePatientIdentificationCodedValues';

export const IdentificationEntryFields = () => {
    const { control } = useFormContext<IdentificationEntry>();

    const coded = usePatientIdentificationCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="As of:"
                        orientation="horizontal"
                        onBlur={onBlur}
                        defaultValue={value}
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
                name="type"
                rules={{ required: { value: true, message: 'Type is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SelectInput
                        label="Type:"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        htmlFor={name}
                        name={name}
                        id={name}
                        options={coded.types}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="value"
                rules={{ required: { value: true, message: 'ID # is required.' }, ...maxLengthRule(100) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="ID #:"
                        orientation="horizontal"
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        htmlFor={name}
                        name={name}
                        id={name}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="state"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Issued state:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        htmlFor={name}
                        name={name}
                        id={name}
                        options={coded.authorities}
                    />
                )}
            />
        </section>
    );
};
