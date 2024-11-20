import { usePatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { IdentificationEntry } from '../entry';

export const IdentificationEntryFields = () => {
    const { control } = useFormContext<IdentificationEntry>();

    const coded = usePatientIdentificationCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validateRequiredRule('as of date') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Identification as of"
                        orientation="horizontal"
                        onBlur={onBlur}
                        defaultValue={value}
                        onChange={onChange}
                        name={`identification-${name}`}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ ...validateRequiredRule('type') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Type"
                        orientation="horizontal"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`identification-${name}`}
                        name={`identification-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="issuer"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Assigning authority"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        id={name}
                        options={coded.authorities}
                    />
                )}
            />
            <Controller
                control={control}
                name="id"
                rules={{ ...validateRequiredRule('id value'), ...maxLengthRule(100, 'id value') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="ID value"
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
        </section>
    );
};
