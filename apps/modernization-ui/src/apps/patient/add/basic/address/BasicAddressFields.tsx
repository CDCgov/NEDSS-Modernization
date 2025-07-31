import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { validCensusTractRule, CensusTractInputField } from 'apps/patient/data/address';
import { Input } from 'components/FormInputs/Input';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { validZipCodeRule, ZipCodeInputField } from 'libs/demographics/location';
import { maxLengthRule } from 'validation/entry';
import { BasicNewPatientEntry } from 'apps/patient/add/basic/entry';
import { TextInputField } from 'design-system/input';
import { useCountryOptions, useCountyOptions, useStateOptions } from 'options/location';

const STREET_ADDRESS_LABEL = 'Street address 1';
const STREET_ADDRESS_2_LABEL = 'Street address 2';
const CITY_LABEL = 'City';
const ZIP_LABEL = 'Zip';
const CENSUS_TRACT_LABEL = 'Census tract';

export const BasicAddressFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    groupName = ''
}: EntryFieldsProps & { groupName?: string }) => {
    const { control } = useFormContext<BasicNewPatientEntry>();
    const selectedState = useWatch({ control, name: 'address.state' });

    const countries = useCountryOptions();
    const states = useStateOptions();
    const counties = useCountyOptions(selectedState?.value);

    return (
        <>
            <Controller
                control={control}
                name="address.address1"
                rules={maxLengthRule(100, STREET_ADDRESS_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={STREET_ADDRESS_LABEL}
                        orientation={orientation}
                        sizing={sizing}
                        id={name}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                        aria-label={`${groupName} ${STREET_ADDRESS_LABEL}`}
                    />
                )}
            />
            <Controller
                control={control}
                name="address.address2"
                rules={maxLengthRule(100, STREET_ADDRESS_2_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={STREET_ADDRESS_2_LABEL}
                        orientation={orientation}
                        sizing={sizing}
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                        ariaLabel={`${groupName} ${STREET_ADDRESS_2_LABEL}`}
                    />
                )}
            />
            <Controller
                control={control}
                name="address.city"
                rules={maxLengthRule(100, CITY_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={CITY_LABEL}
                        orientation={orientation}
                        sizing={sizing}
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                        ariaLabel={`${groupName} ${CITY_LABEL}`}
                    />
                )}
            />
            <Controller
                control={control}
                name="address.state"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label="State"
                        orientation={orientation}
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={states}
                        aria-label={`${groupName} State`}
                    />
                )}
            />
            <Controller
                control={control}
                name="address.zipcode"
                rules={validZipCodeRule(ZIP_LABEL)}
                render={({ field: { onChange, value, name, onBlur }, fieldState: { error } }) => (
                    <ZipCodeInputField
                        id={name}
                        label={ZIP_LABEL}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        orientation={orientation}
                        sizing={sizing}
                        error={error?.message}
                        aria-label={`${groupName} ${ZIP_LABEL}`}
                    />
                )}
            />
            <Controller
                control={control}
                name="address.county"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label="County"
                        orientation={orientation}
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={counties}
                        aria-label={`${groupName} County`}
                    />
                )}
            />
            <Controller
                control={control}
                name="address.censusTract"
                rules={validCensusTractRule(CENSUS_TRACT_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <CensusTractInputField
                        id={name}
                        label={CENSUS_TRACT_LABEL}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        orientation={orientation}
                        sizing={sizing}
                        error={error?.message}
                        aria-label={`${groupName} ${CENSUS_TRACT_LABEL}`}
                    />
                )}
            />
            <Controller
                control={control}
                name="address.country"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label="Country"
                        orientation={orientation}
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={countries}
                        autoComplete="off"
                        aria-label={`${groupName} Country`}
                    />
                )}
            />
        </>
    );
};
