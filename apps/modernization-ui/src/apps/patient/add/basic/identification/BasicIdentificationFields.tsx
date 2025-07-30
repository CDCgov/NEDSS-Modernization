import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { BasicIdentificationEntry } from '../entry';
import { useIdentificationCodedValues } from 'apps/patient/data/identification/useIdentificationCodedValues';

const TYPE_LABEL = 'Type';
const ID_VALUE_LABEL = 'ID value';

export const BasicIdentificationFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    groupName = ''
}: EntryFieldsProps & { groupName?: string }) => {
    const { control } = useFormContext<BasicIdentificationEntry>();

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
                        sizing={sizing}
                        onChange={onChange}
                        id={`identification-${name}`}
                        options={useIdentificationCodedValues().types}
                        error={error?.message}
                        required
                        aria-label={`${groupName} ${TYPE_LABEL}`}
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
                        sizing={sizing}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        options={useIdentificationCodedValues().authorities}
                        aria-label={`${groupName} Assigning authority`}
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
                        sizing={sizing}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                        required
                        ariaLabel={`${groupName} ${ID_VALUE_LABEL}`}
                    />
                )}
            />
        </section>
    );
};
