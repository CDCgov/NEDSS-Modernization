import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { TextInputField } from 'design-system/input';
import { EntryFieldsProps } from 'design-system/entry';
import { validateExtendedNameRule, validateRequiredRule } from 'validation/entry/';

import { NameDemographic, labels } from '../names';
import { NameOptions } from './useNameOptions';

type NameDemographicFieldsProps = { options: NameOptions } & EntryFieldsProps;

const NameDemographicFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    options
}: NameDemographicFieldsProps) => {
    const { control } = useFormContext<NameDemographic>();

    return (
        <>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validDateRule(labels.asOf), ...validateRequiredRule(labels.asOf) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={labels.asOf}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        orientation={orientation}
                        error={error?.message}
                        required
                        sizing={sizing}
                        aria-description="This date defaults to today and can be changed if needed"
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ ...validateRequiredRule(labels.type) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={labels.type}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={options.types}
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
                        label={labels.prefix}
                        orientation={orientation}
                        value={value}
                        id={name}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        options={options.prefixes}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="last"
                rules={{ ...validateExtendedNameRule(labels.last) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.last}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
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
                rules={{ ...validateExtendedNameRule(labels.secondLast) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.secondLast}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
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
                rules={{ ...validateExtendedNameRule(labels.first) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.first}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
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
                rules={validateExtendedNameRule(labels.middle)}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.middle}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
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
                rules={{ ...validateExtendedNameRule(labels.secondMiddle) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.secondMiddle}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
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
                        label={labels.suffix}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.suffixes}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="degree"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label={labels.degree}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={options.degrees}
                        sizing={sizing}
                    />
                )}
            />
        </>
    );
};

export { NameDemographicFields };
