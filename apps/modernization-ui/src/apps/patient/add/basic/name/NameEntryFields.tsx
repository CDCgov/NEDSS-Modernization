import { useNameCodedValues } from 'apps/patient/data/name/useNameCodedValues';
import { Input } from 'components/FormInputs/Input';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import { validateExtendedNameRule } from 'validation/entry';

import { NameInformationEntry } from '../entry';

export const NameEntryFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control } = useFormContext<{ name: NameInformationEntry }>();
    const { suffixes } = useNameCodedValues();

    return (
        <>
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
                    />
                )}
            />
            <Controller
                control={control}
                name="name.middle"
                rules={{ ...validateExtendedNameRule('Middle name') }}
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
                        options={suffixes}
                    />
                )}
            />
        </>
    );
};
