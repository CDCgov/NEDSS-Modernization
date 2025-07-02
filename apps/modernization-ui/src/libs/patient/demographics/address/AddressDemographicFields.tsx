import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { AddressDemographic } from './address';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { maxLengthRule, validateExtendedNameRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { useAddressCodedValues } from './useAddressCodedValues';
import { TextInputField } from 'design-system/input';
import { useCountryOptions, useCountyOptions, useStateOptions } from 'options/location';
import { TextAreaField } from 'design-system/input/text/TextAreaField';

const AS_OF_DATE_LABEL = 'Address as of';
const TYPE_LABEL = 'Type';
const USE_LABEL = 'Use';
const ADDRESS_1_LABEL = 'Street address 1';
const ADDRESS_2_LABEL = 'Street address 2';
const CITY_LABEL = 'City';
const STATE_LABEL = 'State';
const ZIP_LABEL = 'Zip';
const COUNTY_LABEL = 'County';
const COUNTRY_LABEL = 'Country';
const CENSUS_TRACT_LABEL = 'Census tract';
const COMMENTS_LABEL = 'Address comments';

type AddressDemographicFieldsProps = {} & EntryFieldsProps;

const AddressDemographicFields = ({ orientation = 'horizontal', sizing = 'medium' }: AddressDemographicFieldsProps) => {
    const { control } = useFormContext<AddressDemographic>();
    const coded = useAddressCodedValues();

    const selectedState = useWatch({ control, name: 'state' });

    const states = useStateOptions();
    const county = useCountyOptions(selectedState?.name);
    const country = useCountryOptions();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validDateRule(AS_OF_DATE_LABEL), ...validateRequiredRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={AS_OF_DATE_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        orientation={orientation}
                        error={error?.message}
                        required
                        sizing={sizing}
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
                        id={`name-${name}`}
                        name={`name-${name}`}
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
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={USE_LABEL}
                        orientation={orientation}
                        value={value}
                        id={name}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        options={coded.uses}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="address1"
                rules={{ ...validateExtendedNameRule('Street address 1') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={ADDRESS_1_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="address2"
                rules={{ ...validateExtendedNameRule('Street address 2') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={ADDRESS_2_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="city"
                rules={{ ...validateExtendedNameRule('City') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={CITY_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="state"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={STATE_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={states}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="zipcode"
                rules={{ ...validateExtendedNameRule('Zip') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={ZIP_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="county"
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <SingleSelect
                        label={COUNTY_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={county}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="censusTract"
                rules={{ ...validateExtendedNameRule('Census tract') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={CENSUS_TRACT_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
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
                        label={COUNTRY_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={country}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="comment"
                rules={{ ...maxLengthRule(2000, 'Address comments') }}
                render={({ field: { onChange, value, name } }) => (
                    <TextAreaField
                        label={COMMENTS_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};

export { AddressDemographicFields };
