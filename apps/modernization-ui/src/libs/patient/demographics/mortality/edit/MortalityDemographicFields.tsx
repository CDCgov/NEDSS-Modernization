import { useEffect } from 'react';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { isEqual } from 'options';
import { indicators } from 'options/indicator';
import { Shown } from 'conditional-render';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { TextInputField } from 'design-system/input';
import { MoralityOptions } from './useMortalityOptions';
import { HasMortalityDemographic, labels } from '../mortality';

const isDeceased = isEqual(indicators.yes);

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

    const selectedDeceased = useWatch({
        control: form.control,
        name: 'mortality.deceased'
    });

    useEffect(() => {
        if (!selectedState) {
            form.setValue('mortality.county', null);
        }
        options.location.state(selectedState);
    }, [selectedState?.value, options.location.state, form.setValue]);

    return (
        <>
            <Controller
                control={form.control}
                name="mortality.asOf"
                rules={{ ...validateRequiredRule(labels.asOf), ...validDateRule(labels.asOf) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={labels.asOf}
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
                            options={options.location.states}
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
                            options={options.location.counties}
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
                            options={options.location.countries}
                            sizing={sizing}
                        />
                    )}
                />
            </Shown>
        </>
    );
};

export { MortalityDemographicFields, type MortalityDemographicFieldsProps };
