import { usePatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { IdentificationEntry } from '../entry';

export const IdentificationEntryFields = () => {
    const { control } = useFormContext<IdentificationEntry>();

    const coded = usePatientIdentificationCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Identification as of"
                        orientation="horizontal"
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
                rules={{ required: { value: true, message: 'Type is required.' } }}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Type"
                        orientation="horizontal"
                        value={value}
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
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label="Assigning authority"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        name={name}
                        id={name}
                        options={coded.authorities}
                    />
                )}
            />
            <Controller
                control={control}
                name="id"
                rules={{ required: { value: true, message: 'ID value is required.' }, ...maxLengthRule(100) }}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="ID value"
                        orientation="horizontal"
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
