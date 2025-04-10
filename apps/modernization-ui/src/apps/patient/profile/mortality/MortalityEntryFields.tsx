import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { MortalityEntry } from './MortalityEntry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Indicator, indicators } from 'coded';
import { maxLengthRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { useCountryCodedValues } from 'apps/patient/data/country/useCountryCodedValues';
import { useCountyCodedValues } from 'apps/patient/data/county/useCountyCodedValues';
import { useStateCodedValues } from 'apps/patient/data/state/useStateCodedValues';

export const MortalityEntryFields = () => {
    const { control } = useFormContext<MortalityEntry>();
    const selectedState = useWatch({ control, name: 'state' });
    const selectedDeceased = useWatch({ control, name: 'deceased' });

    const counties = useCountyCodedValues(selectedState ?? '');

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="As of:"
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
                name="deceased"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Is the patient deceased:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={indicators}
                    />
                )}
            />
            {selectedDeceased && selectedDeceased === Indicator.Yes && (
                <>
                    <Controller
                        control={control}
                        name="deceasedOn"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <DatePickerInput
                                label="Date of death:"
                                orientation="horizontal"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                name={name}
                                disableFutureDates
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="city"
                        rules={maxLengthRule(100)}
                        render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                            <Input
                                label="City of death:"
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
                        name="state"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SelectInput
                                label="State of death:"
                                orientation="horizontal"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                id={name}
                                name={name}
                                htmlFor={name}
                                options={useStateCodedValues({ lazy: false }).options}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="county"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SelectInput
                                label="County of death:"
                                orientation="horizontal"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                id={name}
                                name={name}
                                htmlFor={name}
                                options={counties.options}
                            />
                        )}
                    />

                    <Controller
                        control={control}
                        name="country"
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <SelectInput
                                label="Country of death:"
                                orientation="horizontal"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                id={name}
                                name={name}
                                htmlFor={name}
                                options={useCountryCodedValues({ lazy: false }).options}
                            />
                        )}
                    />
                </>
            )}
        </section>
    );
};
