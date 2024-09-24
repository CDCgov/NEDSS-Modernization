import { Indicator, indicators } from 'coded';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { useLocationCodedValues } from 'location';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { MortalityEntry } from '../entry';
import { useEffect } from 'react';

export const MortalityEntryFields = () => {
    const { control, reset, getValues } = useFormContext<{ mortality: MortalityEntry }>();
    const selectedState = useWatch({ control, name: 'mortality.state' });
    const selectedDeceased = useWatch({ control, name: 'mortality.deceased' });

    const location = useLocationCodedValues();
    const counties = location.counties.byState(selectedState?.value);

    useEffect(() => {
        if (selectedDeceased?.value !== Indicator.Yes) {
            reset(
                {
                    ...getValues(),
                    mortality: {
                        deceasedOn: undefined,
                        state: undefined,
                        city: undefined,
                        county: undefined,
                        country: undefined
                    }
                },
                { keepDefaultValues: true }
            );
        }
    }, [selectedDeceased?.value]);

    return (
        <section>
            <Controller
                control={control}
                name="mortality.asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Mortality information as of"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="mortality.deceased"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Is the patient deceased"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={indicators}
                    />
                )}
            />
            {selectedDeceased && selectedDeceased.value === Indicator.Yes && (
                <>
                    <Controller
                        control={control}
                        name="mortality.deceasedOn"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <DatePickerInput
                                label="Date of death"
                                orientation="horizontal"
                                defaultValue={value ?? ''}
                                onChange={onChange}
                                onBlur={onBlur}
                                name={name}
                                disableFutureDates
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="mortality.city"
                        rules={maxLengthRule(100)}
                        render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                            <Input
                                label="Death city"
                                orientation="horizontal"
                                onChange={onChange}
                                onBlur={onBlur}
                                type="text"
                                defaultValue={value}
                                id={name}
                                name={name}
                                htmlFor={name}
                                error={error?.message}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="mortality.state"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SingleSelect
                                label="Death state"
                                orientation="horizontal"
                                value={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                id={name}
                                name={name}
                                options={location.states.all}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="mortality.county"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SingleSelect
                                label="Death county"
                                orientation="horizontal"
                                value={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                id={name}
                                name={name}
                                options={counties}
                            />
                        )}
                    />

                    <Controller
                        control={control}
                        name="mortality.country"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SingleSelect
                                label="Death country"
                                orientation="horizontal"
                                value={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                id={name}
                                name={name}
                                options={location.countries}
                            />
                        )}
                    />
                </>
            )}
        </section>
    );
};
