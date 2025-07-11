import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { TextInputField } from 'design-system/input';
import { EntryFieldsProps } from 'design-system/entry';
import { validateExtendedNameRule, validateRequiredRule } from 'validation/entry/';

import { NameDemographic } from './names';
import { useNameCodedValues } from './useNameCodedValues';

const AS_OF_DATE_LABEL = 'Name as of';
const TYPE_LABEL = 'Type';

type NameDemographicFieldsProps = {} & EntryFieldsProps;

const NameDemographicFields = ({ orientation = 'horizontal', sizing = 'medium' }: NameDemographicFieldsProps) => {
    const { control } = useFormContext<NameDemographic>();
    const coded = useNameCodedValues();

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
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                        sizing={sizing}
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
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="last"
                rules={{ ...validateExtendedNameRule('Last name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label="Last"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="secondLast"
                rules={{ ...validateExtendedNameRule('Second last name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label="Second last"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="first"
                rules={{ ...validateExtendedNameRule('First name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label="First"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="middle"
                rules={validateExtendedNameRule('Middle name')}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label="Middle"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="secondMiddle"
                rules={{ ...validateExtendedNameRule('Second middle name') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label="Second middle"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};

export { NameDemographicFields };
