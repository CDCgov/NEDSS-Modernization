import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { validZipCodeRule, ZipCodeInputField } from 'libs/demographics/location';
import { CensusTractInputField, validCensusTractRule } from './census-tract';
import { AddressEntry } from './entry';
import { TextAreaField } from 'design-system/input/text/TextAreaField';
import { useAddressCodedValues } from './useAddressCodedValues';
import { useCountryOptions, useCountyOptions, useStateOptions } from 'options/location';
import { TextInputField } from 'design-system/input';

const AS_OF_DATE_LABEL = 'Address as of';
const TYPE_LABEL = 'Type';
const USE_LABEL = 'Use';
const STREET_ADDRESS_LABEL = 'Street address 1';
const STREET_ADDRESS_2_LABEL = 'Street address 2';
const CITY_LABEL = 'City';
const ZIP_LABEL = 'Zip';
const CENSUS_TRACT_LABEL = 'Census tract';
const COMMENTS_LABEL = 'Address comments';

export const AddressEntryFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control } = useFormContext<AddressEntry>();
    const coded = useAddressCodedValues();

    const selectedState = useWatch({ control, name: 'state' });

    const countries = useCountryOptions();
    const states = useStateOptions();
    const counties = useCountyOptions(selectedState?.value);

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
                        sizing={sizing}
                        aria-description="This date defaults to today and can be changed if needed"
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
                        sizing={sizing}
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
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="address1"
                rules={maxLengthRule(100, STREET_ADDRESS_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={STREET_ADDRESS_LABEL}
                        orientation={orientation}
                        id={name}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="address2"
                rules={maxLengthRule(100, STREET_ADDRESS_2_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={STREET_ADDRESS_2_LABEL}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        value={value}
                        type="text"
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="city"
                rules={maxLengthRule(100, CITY_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={CITY_LABEL}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        value={value}
                        type="text"
                        id={name}
                        error={error?.message}
                        sizing={sizing}
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
                        options={states}
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        options={countries}
                        sizing={sizing}
                        autoComplete="off"
                    />
                )}
            />

            <Controller
                control={control}
                name="comment"
                rules={maxLengthRule(2000, COMMENTS_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextAreaField
                        label={COMMENTS_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};
