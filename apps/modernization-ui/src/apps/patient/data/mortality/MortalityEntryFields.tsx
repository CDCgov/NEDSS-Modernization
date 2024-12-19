import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { Indicator, indicators } from 'coded';
import { EntryFieldsProps } from 'design-system/entry';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { useLocationCodedValues } from 'location';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { MortalityEntry } from 'apps/patient/data/entry';

const AS_OF_DATE_LABEL = 'Mortality information as of';
const DECEASED_ON_LABEL = 'Date of death';
const DEATH_CITY_LABEL = 'Death city';

type MortalityEntryFieldsProps = EntryFieldsProps;

export const MortalityEntryFields = ({ orientation = 'horizontal' }: MortalityEntryFieldsProps) => {
    const { control, resetField } = useFormContext<{ mortality: MortalityEntry }>();
    const selectedState = useWatch({ control, name: 'mortality.state' });
    const selectedDeceased = useWatch({ control, name: 'mortality.deceased' });

    const location = useLocationCodedValues();
    const counties = location.counties.byState(selectedState?.value);

    useEffect(() => {
        if (selectedDeceased?.value !== Indicator.Yes) {
            resetField('mortality.deceasedOn');
            resetField('mortality.state');
            resetField('mortality.city');
            resetField('mortality.county');
            resetField('mortality.country');
        }
    }, [selectedDeceased?.value]);

    return (
        <section>
            <Controller
                control={control}
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
                    />
                )}
            />
            <Controller
                control={control}
                name="mortality.deceased"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Is the patient deceased?"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={indicators}
                    />
                )}
            />
            {selectedDeceased?.value === Indicator.Yes && (
                <>
                    <Controller
                        control={control}
                        name="mortality.deceasedOn"
                        shouldUnregister
                        rules={validDateRule(DECEASED_ON_LABEL)}
                        render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                            <DatePickerInput
                                id={name}
                                label={DECEASED_ON_LABEL}
                                orientation={orientation}
                                value={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                error={error?.message}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="mortality.city"
                        shouldUnregister
                        rules={maxLengthRule(100, DEATH_CITY_LABEL)}
                        render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                            <Input
                                label={DEATH_CITY_LABEL}
                                orientation={orientation}
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
                        shouldUnregister
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SingleSelect
                                label="Death state"
                                orientation={orientation}
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
                        shouldUnregister
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SingleSelect
                                label="Death county"
                                orientation={orientation}
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
                        shouldUnregister
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SingleSelect
                                label="Death country"
                                orientation={orientation}
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
