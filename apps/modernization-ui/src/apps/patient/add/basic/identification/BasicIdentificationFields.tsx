import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { usePatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { BasicIdentificationEntry } from '../entry';

const TYPE_LABEL = 'Type';
const ID_VALUE_LABEL = 'ID value';

type IdentificationEntryFieldsProps = EntryFieldsProps;

export const BasicIdentificationFields = ({ orientation = 'horizontal' }: IdentificationEntryFieldsProps) => {
    const { control } = useFormContext<BasicIdentificationEntry>();
    const coded = usePatientIdentificationCodedValues();

    return (
        <section>
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
                        sizing="compact"
                        onChange={onChange}
                        id={`identification-${name}`}
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
                        orientation={orientation}
                        value={value}
                        sizing="compact"
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        options={coded.authorities}
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
                        sizing="compact"
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                        required
                    />
                )}
            />
        </section>
    );
};