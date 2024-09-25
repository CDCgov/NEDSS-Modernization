import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';
import { usePatientAddressCodedValues } from 'apps/patient/profile/addresses/usePatientAddressCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { useLocationCodedValues } from 'location';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { AddressEntry } from '../entry';

export const AddressEntryFields = () => {
    const { control, reset } = useFormContext<AddressEntry>();
    const coded = usePatientAddressCodedValues();
    const location = useLocationCodedValues();
    const selectedState = useWatch({ control, name: 'state' });
    const enteredCity = useWatch({ control, name: 'city' });
    const enteredZip = useWatch({ control, name: 'zipcode' });
    const counties = location.counties.byState(selectedState?.value ?? '');

    const handleSuggestionSelection = (selected: AddressSuggestion) => {
        reset(
            {
                address1: selected.address1,
                city: selected.city,
                state: selected.state ?? undefined,
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
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Address as of"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name="addressAsof"
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
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Type"
                        orientation="horizontal"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id="addressType"
                        name="addressType"
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
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Use"
                        orientation="horizontal"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id="addressUse"
                        name="addressUse"
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
                        locations={location}
                        criteria={{
                            city: enteredCity ?? undefined,
                            state: selectedState?.value ?? undefined,
                            zip: enteredZip ?? undefined
                        }}
                        defaultValue={value ?? ''}
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
                    <SingleSelect
                        label="State"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={location.states.all}
                    />
                )}
            />
            <Controller
                control={control}
                name="zipcode"
                rules={{
                    pattern: {
                        value: /^\d{5}(?:[-\s]\d{4})?$/,
                        message: 'Please enter a valid ZIP code (XXXXX) using only numeric characters (0-9).'
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
                    <SingleSelect
                        label="County"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={counties}
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
                    <SingleSelect
                        label="Country"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={location.countries}
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
