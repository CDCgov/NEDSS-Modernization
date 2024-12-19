import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { EntryFieldsProps } from 'design-system/entry';
import { Controller, useFormContext } from 'react-hook-form';
import { NameInformationEntry } from '../entry';
import { validateExtendedNameRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';

type NameEntryFieldsProps = EntryFieldsProps;

export const NameEntryFields = ({ orientation = 'horizontal' }: NameEntryFieldsProps) => {
    const { control } = useFormContext<{ name: NameInformationEntry }>();
    const coded = usePatientNameCodedValues();
    return (
        <section>
            <Controller
                control={control}
                name="name.last"
                rules={{ ...validateExtendedNameRule('Last name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Last"
                        sizing="compact"
                        orientation={orientation}
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
                name="name.first"
                rules={{ ...validateExtendedNameRule('First name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="First"
                        sizing="compact"
                        orientation={orientation}
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
                name="name.middle"
                rules={validateExtendedNameRule('Middle name')}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Middle"
                        sizing="compact"
                        orientation={orientation}
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
                name="name.suffix"
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <SingleSelect
                        label="Suffix"
                        orientation={orientation}
                        sizing="compact"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.suffixes}
                    />
                )}
            />
        </section>
    );
};
