import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller, useFormContext } from 'react-hook-form';
import { validNameRule } from 'validation/entry';
import { NameEntry } from './NameEntry';
import { usePatientNameCodedValues } from './usePatientNameCodedValues';

export const NameEntryFields = () => {
    const { control } = useFormContext<NameEntry>();
    const coded = usePatientNameCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Name as of"
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
                        label="Type"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        htmlFor={name}
                        id={name}
                        onChange={onChange}
                        name={name}
                        options={coded.types}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="prefix"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Prefix"
                        orientation="horizontal"
                        defaultValue={value}
                        htmlFor={name}
                        id={name}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        options={coded.prefixes}
                    />
                )}
            />

            <Controller
                control={control}
                name="last"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Last"
                        orientation="horizontal"
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="secondLast"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Second last"
                        orientation="horizontal"
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="first"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="First"
                        orientation="horizontal"
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="middle"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Middle"
                        orientation="horizontal"
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="secondMiddle"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Second middle"
                        orientation="horizontal"
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="suffix"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        label="Suffix"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        htmlFor={name}
                        id={name}
                        name={name}
                        options={coded.suffixes}
                    />
                )}
            />
            <Controller
                control={control}
                name="degree"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        label="Degree"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        htmlFor={name}
                        id={name}
                        name={name}
                        options={coded.degrees}
                    />
                )}
            />
        </section>
    );
};
