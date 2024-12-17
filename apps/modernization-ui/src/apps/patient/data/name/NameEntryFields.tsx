import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { EntryFieldsProps } from 'design-system/entry';
import { validateExtendedNameRule, validateRequiredRule } from 'validation/entry/';
import { NameEntry } from './entry';
import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';

const AS_OF_DATE_LABEL = 'Name as of';
const TYPE_LABEL = 'Type';

type NameEntryFieldsProps = EntryFieldsProps;

export const NameEntryFields = ({ orientation = 'horizontal' }: NameEntryFieldsProps) => {
    const { control } = useFormContext<NameEntry>();
    const coded = usePatientNameCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validDateRule(AS_OF_DATE_LABEL), ...validateRequiredRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={AS_OF_DATE_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        orientation={orientation}
                        error={error?.message}
                        required
                        sizing="compact"
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
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                        sizing="compact"
                    />
                )}
            />
            <Controller
                control={control}
                name="prefix"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Prefix"
                        orientation={orientation}
                        value={value}
                        id={name}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        options={coded.prefixes}
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={control}
                name="last"
                rules={{ ...validateExtendedNameRule('Last name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Last"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={control}
                name="secondLast"
                rules={{ ...validateExtendedNameRule('Second last name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Second last"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={control}
                name="first"
                rules={{ ...validateExtendedNameRule('First name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="First"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={control}
                name="middle"
                rules={validateExtendedNameRule('Middle name')}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Middle"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={control}
                name="secondMiddle"
                rules={{ ...validateExtendedNameRule('Second middle name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Second middle"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={control}
                name="suffix"
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <SingleSelect
                        label="Suffix"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.suffixes}
                        sizing="compact"
                    />
                )}
            />
            <Controller
                control={control}
                name="degree"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label="Degree"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={coded.degrees}
                        sizing="compact"
                    />
                )}
            />
        </section>
    );
};
