import { useEffect } from 'react';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { AgeResolver } from 'date';
import { isEqual } from 'options';
import { Shown } from 'conditional-render';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { NumericInput, TextInputField } from 'design-system/input';
import { ValueField } from 'design-system/field';
import { HasSexBirthDemographic, labels } from '../sexBirth';
import { SexBirthOptions } from './useSexBirthOptions';

type SexBirthDemographicFieldsProps = {
    form: UseFormReturn<HasSexBirthDemographic>;
    options: SexBirthOptions;
    ageResolver: AgeResolver;
} & EntryFieldsProps;

const SexBirthDemographicFields = ({
    form,
    options,
    ageResolver,
    orientation = 'horizontal',
    sizing
}: SexBirthDemographicFieldsProps) => {
    const currentBirthday = useWatch({ control: form.control, name: 'sexBirth.bornOn' });
    const selectedCurrentGender = useWatch({ control: form.control, name: 'sexBirth.current' });
    const selectedState = useWatch({ control: form.control, name: 'sexBirth.state' });
    const selectedMultipleBirth = useWatch({ control: form.control, name: 'sexBirth.multiple' });

    const age = ageResolver(currentBirthday);

    const isMultipleBirth = isEqual(options.multipleBirth.yes);
    const isUnknownGender = isEqual(options.genders.unknown);

    useEffect(() => {
        if (!selectedState) {
            form.setValue('sexBirth.county', undefined);
        }
        options.location.state(selectedState);
    }, [selectedState?.value, options.location.state, form.setValue]);

    useEffect(() => {
        if (!isUnknownGender(selectedCurrentGender)) {
            form.setValue('sexBirth.unknownReason', undefined);
        }
    }, [selectedCurrentGender, form.setValue]);

    return (
        <>
            <Controller
                control={form.control}
                name="sexBirth.asOf"
                rules={{ ...validateRequiredRule(labels.asOf), ...validDateRule(labels.asOf) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label={labels.asOf}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        error={error?.message}
                        required
                        sizing={sizing}
                        aria-description="This field defaults to today's date and can be changed if needed."
                    />
                )}
            />
            <Controller
                control={form.control}
                name="sexBirth.bornOn"
                rules={validDateRule(labels.bornOn)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        sizing={sizing}
                        label={labels.bornOn}
                        error={error?.message}
                        orientation={orientation}
                    />
                )}
            />
            <ValueField label="Current age" sizing={sizing}>
                {age}
            </ValueField>
            <Controller
                control={form.control}
                name="sexBirth.current"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.current}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.genders.all}
                        sizing={sizing}
                    />
                )}
            />
            <Shown when={isUnknownGender(selectedCurrentGender)}>
                <Controller
                    control={form.control}
                    name="sexBirth.unknownReason"
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <SingleSelect
                            label={labels.unknownReason}
                            orientation={orientation}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            options={options.genderUnknownReasons}
                            sizing={sizing}
                        />
                    )}
                />
            </Shown>
            <Controller
                control={form.control}
                name="sexBirth.transgenderInformation"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.transgenderInformation}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.preferredGenders}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="sexBirth.additionalGender"
                rules={maxLengthRule(20)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.additionalGender}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        type="text"
                        id={name}
                        name={name}
                        value={value}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="sexBirth.sex"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.sex}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.genders.all}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="sexBirth.multiple"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.multiple}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.multipleBirth.all}
                        sizing={sizing}
                    />
                )}
            />
            <Shown when={isMultipleBirth(selectedMultipleBirth)}>
                <Controller
                    control={form.control}
                    name="sexBirth.order"
                    shouldUnregister
                    rules={{
                        min: { value: 0, message: 'Must be a positive number.' },
                        max: { value: 100000, message: 'Must be less than 100000.' }
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <NumericInput
                            label={labels.order}
                            id={name}
                            name={name}
                            value={value}
                            max={100000}
                            maxLength={5}
                            onChange={onChange}
                            onBlur={onBlur}
                            error={error?.message}
                            sizing={sizing}
                            orientation={orientation}
                        />
                    )}
                />
            </Shown>
            <Controller
                control={form.control}
                name="sexBirth.city"
                rules={maxLengthRule(100, labels.city)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.city}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        value={value}
                        id={name}
                        name={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="sexBirth.state"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.state}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.location.states}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="sexBirth.county"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.county}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.location.counties}
                        sizing={sizing}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="sexBirth.country"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.country}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.location.countries}
                        sizing={sizing}
                    />
                )}
            />
        </>
    );
};

export { SexBirthDemographicFields };
