import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { AddressFields } from './AddressEntry';
import { usePatientAddressCodedValues } from './usePatientAddressCodedValues';
import { useCountyCodedValues } from 'apps/patient/data/county/useCountyCodedValues';
import { useCountryCodedValues } from 'apps/patient/data/country/useCountryCodedValues';
import { useStateCodedValues } from 'apps/patient/data/state/useStateCodedValues';
import { initial } from 'location';

export const AddressEntryFields = () => {
    const { control, reset } = useFormContext<AddressFields>();
    const coded = usePatientAddressCodedValues();
    const selectedState = useWatch({ control, name: 'state' });
    const enteredCity = useWatch({ control, name: 'city' });
    const enteredZip = useWatch({ control, name: 'zipcode' });
    const counties = useCountyCodedValues(selectedState ?? '');

    const handleSuggestionSelection = (selected: AddressSuggestion) => {
        reset(
            {
                address1: selected.address1,
                city: selected.city,
                state: selected.state?.value ?? '',
                zipcode: selected.zip
            },
            { keepDefaultValues: true }
        );
    };

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Address as of"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ required: { value: true, message: 'Type is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SelectInput
                        label="Type"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.types}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="use"
                rules={{ required: { value: true, message: 'Use is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SelectInput
                        label="Use"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
                        htmlFor={name}
                        name={name}
                        options={coded.uses}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="address1"
                rules={maxLengthRule(100)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <AddressSuggestionInput
                        label="Street address 1"
                        orientation="horizontal"
                        id={name}
                        locations={initial}
                        criteria={{
                            city: enteredCity ?? undefined,
                            state: selectedState ?? undefined,
                            zip: enteredZip ?? undefined
                        }}
                        value={value ?? undefined}
                        onChange={onChange}
                        onBlur={onBlur}
                        onSelection={handleSuggestionSelection}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="address2"
                rules={maxLengthRule(100)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Street address 2"
                        orientation="horizontal"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="city"
                rules={maxLengthRule(100)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="City"
                        orientation="horizontal"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="state"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        label="State"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        htmlFor={name}
                        id={name}
                        name={name}
                        options={useStateCodedValues({ lazy: false }).options}
                    />
                )}
            />
            <Controller
                control={control}
                name="zipcode"
                rules={{
                    pattern: {
                        value: /^\d{5}(?:[-\s]\d{4})?$/,
                        message:
                            'Please enter a valid ZIP code (XXXXX or XXXXX-XXXX ) using only numeric characters (0-9).'
                    },
                    ...maxLengthRule(20)
                }}
                render={({ field: { onChange, value, name, onBlur }, fieldState: { error } }) => (
                    <Input
                        label="Zip"
                        orientation="horizontal"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name="zipcode"
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="county"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        label="County"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        htmlFor={name}
                        id={name}
                        name={name}
                        options={counties.options}
                    />
                )}
            />
            <Controller
                control={control}
                name="censusTract"
                rules={maxLengthRule(10)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Census tract"
                        orientation="horizontal"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="country"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        label="Country"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={useCountryCodedValues({ lazy: false }).options}
                        autoComplete="off"
                    />
                )}
            />

            <Controller
                control={control}
                name="comment"
                rules={maxLengthRule(2000)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Address comments"
                        orientation="horizontal"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                        multiline
                    />
                )}
            />
        </section>
    );
};
