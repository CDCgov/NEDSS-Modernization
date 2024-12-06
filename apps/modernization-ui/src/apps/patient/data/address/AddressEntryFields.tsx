import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { usePatientAddressCodedValues } from 'apps/patient/profile/addresses/usePatientAddressCodedValues';
import { useLocationCodedValues } from 'location';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';
import {
    validZipCodeRule,
    ZipCodeInputField,
    CensusTractInputField,
    validCensusTractRule
} from 'libs/demographics/location';
import { AddressEntry } from './entry';

const AS_OF_DATE_LABEL = 'Address as of';
const TYPE_LABEL = 'Type';
const USE_LABEL = 'Use';
const STREET_ADDRESS_LABEL = 'Street address 1';
const STREET_ADDRESS_2_LABEL = 'Street address 2';
const CITY_LABEL = 'City';
const ZIP_LABEL = 'Zip';
const CENSUS_TRACT_LABEL = 'Census tract';
const COMMENTS_LABEL = 'Address comments';

type AddressEntryFieldsProps = EntryFieldsProps;

export const AddressEntryFields = ({ orientation = 'horizontal' }: AddressEntryFieldsProps) => {
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
                rules={{ ...validateRequiredRule(AS_OF_DATE_LABEL), ...validDateRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={`address-${name}`}
                        label={AS_OF_DATE_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        orientation={orientation}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ ...validateRequiredRule(TYPE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={TYPE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`address-${name}`}
                        name={`address-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="use"
                rules={{ ...validateRequiredRule(USE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={USE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`address-${name}`}
                        name={`address-${name}`}
                        options={coded.uses}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="address1"
                rules={maxLengthRule(100, STREET_ADDRESS_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <AddressSuggestionInput
                        label={STREET_ADDRESS_LABEL}
                        orientation={orientation}
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
                rules={maxLengthRule(100, STREET_ADDRESS_2_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={STREET_ADDRESS_2_LABEL}
                        orientation={orientation}
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
                rules={maxLengthRule(100, CITY_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={CITY_LABEL}
                        orientation={orientation}
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
                        orientation={orientation}
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
                rules={validZipCodeRule(ZIP_LABEL)}
                render={({ field: { onChange, value, name, onBlur }, fieldState: { error } }) => (
                    <ZipCodeInputField
                        id={name}
                        label={ZIP_LABEL}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        orientation={orientation}
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
                        orientation={orientation}
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
                rules={validCensusTractRule(CENSUS_TRACT_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <CensusTractInputField
                        id={name}
                        label={CENSUS_TRACT_LABEL}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        orientation={orientation}
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
                        orientation={orientation}
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
                rules={maxLengthRule(2000, COMMENTS_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={COMMENTS_LABEL}
                        orientation={orientation}
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
