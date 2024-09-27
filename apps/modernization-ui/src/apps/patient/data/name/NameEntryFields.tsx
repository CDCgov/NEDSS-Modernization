import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { Controller, useFormContext } from 'react-hook-form';
import { validNameRule } from 'validation/entry';
import { NameEntry } from '../entry';
import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { SingleSelect } from 'design-system/select';

export const NameEntryFields = () => {
    const { control } = useFormContext<NameEntry>();
    const coded = usePatientNameCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Name as of"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        name={`name-${name}`}
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
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Type"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="prefix"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label="Prefix"
                        orientation="horizontal"
                        value={value}
                        id={name}
                        onChange={onChange}
                        name={name}
                        options={coded.prefixes}
                    />
                )}
            />

            <Controller
                control={control}
                name="last"
                rules={validNameRule}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Last"
                        orientation="horizontal"
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
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Second last"
                        orientation="horizontal"
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
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="First"
                        orientation="horizontal"
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
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Middle"
                        orientation="horizontal"
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
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Second middle"
                        orientation="horizontal"
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
                    <SingleSelect
                        label="Suffix"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
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
                    <SingleSelect
                        label="Degree"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={coded.degrees}
                    />
                )}
            />
        </section>
    );
};
