import { useEffect } from 'react';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { TextInputField } from 'design-system/input';
import { Shown } from 'conditional-render';
import { isEqual } from 'options';
import { useCountryOptions, useCountyOptions, useStateOptions } from 'options/location';
import { HasMortalityDemographic, labels } from '../mortality';
import { MoralityOptions } from './useMortalityOptions';

const AS_OF_DATE_LABEL = 'Mortality information as of';

type MortalityDemographicFieldsProps = {
    form: UseFormReturn<HasMortalityDemographic>;
    options: MoralityOptions;
} & EntryFieldsProps;

const MortalityDemographicFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    form,
    options
}: MortalityDemographicFieldsProps) => {
    const selectedState = useWatch({ control: form.control, name: 'mortality.state' });
    const selectedDeceased = useWatch({ control: form.control, name: 'mortality.deceased' });

    const isDeceased = isEqual(options.deceased.yes);

    const countries = useCountryOptions();
    const states = useStateOptions();
    const counties = useCountyOptions(selectedState?.value);

    useEffect(() => {
        if (isDeceased(selectedDeceased)) {
            form.resetField('mortality.deceasedOn');
            form.resetField('mortality.state');
            form.resetField('mortality.city');
            form.resetField('mortality.county');
            form.resetField('mortality.country');
        }
    }, [selectedDeceased?.value]);

    return (
        <>
            <Controller
                control={form.control}
                name="mortality.asOf"
                rules={{ ...validateRequiredRule(AS_OF_DATE_LABEL), ...validDateRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={AS_OF_DATE_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="mortality.deceased"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.deceased}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.deceased.all}
                        sizing={sizing}
                    />
                )}
            />

            <Shown when={isDeceased(selectedDeceased)}>
                <Controller
                    control={form.control}
                    name="mortality.deceasedOn"
                    shouldUnregister
                    rules={validDateRule(labels.deceasedOn)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <DatePickerInput
                            id={name}
                            label={labels.deceasedOn}
                            orientation={orientation}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            sizing={sizing}
                            error={error?.message}
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="mortality.city"
                    shouldUnregister
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
                    name="mortality.state"
                    shouldUnregister
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
                    control={form.control}
                    name="mortality.county"
                    shouldUnregister
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
                    control={form.control}
                    name="mortality.country"
                    shouldUnregister
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
            </Shown>
        </>
    );
};

export { MortalityDemographicFields };
