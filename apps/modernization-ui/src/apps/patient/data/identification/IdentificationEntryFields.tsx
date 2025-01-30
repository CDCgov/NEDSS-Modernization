import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { usePatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { IdentificationEntry } from './entry';

const AS_OF_DATE_LABEL = 'Identification as of';
const TYPE_LABEL = 'Type';
const ID_VALUE_LABEL = 'ID value';

export const IdentificationEntryFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control } = useFormContext<IdentificationEntry>();

    const coded = usePatientIdentificationCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validateRequiredRule(AS_OF_DATE_LABEL), ...validDateRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label={AS_OF_DATE_LABEL}
                        id={`identification-${name}`}
                        orientation={orientation}
                        onBlur={onBlur}
                        value={value}
                        onChange={onChange}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ ...validateRequiredRule(TYPE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={TYPE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`identification-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="issuer"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Assigning authority"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        options={coded.authorities}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="id"
                rules={{ ...validateRequiredRule(ID_VALUE_LABEL), ...maxLengthRule(100, ID_VALUE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label={ID_VALUE_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};
