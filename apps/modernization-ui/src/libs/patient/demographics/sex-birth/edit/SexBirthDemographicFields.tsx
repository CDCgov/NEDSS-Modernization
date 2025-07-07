import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { AgeResolver } from 'date';
import { isEqual } from 'options';
import { Shown } from 'conditional-render';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { NumericInput, TextInputField } from 'design-system/input';
import { ValueField } from 'design-system/field';
import { SexBirthDemographic, labels } from '../sexBirth';
import { useSexBirthOptions } from './useSexBirthOptions';
import { useCountryOptions, useCountyOptions, useStateOptions } from 'options/location';

type SexBirthDemographicFieldsProps = {
    ageResolver: AgeResolver;
} & EntryFieldsProps;

const SexBirthDemographicFields = ({
    ageResolver,
    orientation = 'horizontal',
    sizing = 'medium'
}: SexBirthDemographicFieldsProps) => {
    const { control, setValue } = useFormContext<{ birthAndSex: SexBirthDemographic }>();

    const currentBirthday = useWatch({ control, name: 'birthAndSex.bornOn' });
    const selectedCurrentGender = useWatch({ control, name: 'birthAndSex.current' });
    const selectedState = useWatch({ control, name: 'birthAndSex.state' });
    const selectedMultipleBirth = useWatch({ control, name: 'birthAndSex.multiple' });

    const age = ageResolver(currentBirthday);

    const options = useSexBirthOptions();

    const countries = useCountryOptions();
    const states = useStateOptions();
    const counties = useCountyOptions(selectedState?.value);

    const isMultipleBirth = isEqual(options.multipleBirth.yes);
    const isUnknownGender = isEqual(options.genders.unknown);

    useEffect(() => {
        if (!selectedState) {
            setValue('birthAndSex.county', undefined);
        }
    }, [selectedState]);

    useEffect(() => {
        if (!isUnknownGender(selectedCurrentGender)) {
            setValue('birthAndSex.unknownReason', undefined);
        }
    }, [selectedCurrentGender]);

    return (
        <section>
            <Controller
                control={control}
                name="birthAndSex.asOf"
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
                    />
                )}
            />
            <Controller
                control={control}
                name="birthAndSex.bornOn"
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
                control={control}
                name="birthAndSex.current"
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
                    control={control}
                    name="birthAndSex.unknownReason"
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
                control={control}
                name="birthAndSex.transgenderInformation"
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
                control={control}
                name="birthAndSex.additionalGender"
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
                control={control}
                name="birthAndSex.sex"
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
                control={control}
                name="birthAndSex.multiple"
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
                    control={control}
                    name="birthAndSex.order"
                    shouldUnregister
                    rules={{
                        min: { value: 0, message: 'Must be a positive number.' },
                        max: { value: 100000, message: 'Must be less than 100000.' }
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <NumericInput
                            label={labels.multiple}
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
                control={control}
                name="birthAndSex.city"
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
                control={control}
                name="birthAndSex.state"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.state}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={states}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="birthAndSex.county"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.county}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={counties}
                        sizing={sizing}
                    />
                )}
            />

            <Controller
                control={control}
                name="birthAndSex.country"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.country}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={countries}
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};

export { SexBirthDemographicFields };
