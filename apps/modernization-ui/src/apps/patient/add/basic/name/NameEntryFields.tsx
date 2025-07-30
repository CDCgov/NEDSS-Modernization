import { EntryFieldsProps } from 'design-system/entry';
import { Controller, useFormContext } from 'react-hook-form';
import { NameInformationEntry } from '../entry';
import { validateExtendedNameRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { useNameCodedValues } from 'apps/patient/data/name/useNameCodedValues';

type Props = EntryFieldsProps & {
    groupName?: string;
};

export const NameEntryFields = ({ orientation = 'horizontal', sizing = 'medium', groupName = '' }: Props) => {
    const { control } = useFormContext<{ name: NameInformationEntry }>();
    return (
        <section>
            <Controller
                control={control}
                name="name.last"
                rules={{ ...validateExtendedNameRule('Last name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Last"
                        sizing={sizing}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        ariaLabel={`${groupName} Last`}
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
                        sizing={sizing}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        ariaLabel={`${groupName} First`}
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
                        sizing={sizing}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        ariaLabel={`${groupName} Middle`}
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
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={useNameCodedValues().suffixes}
                        aria-label={`${groupName} Suffix`}
                    />
                )}
            />
        </section>
    );
};
