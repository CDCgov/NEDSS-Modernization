import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';
import { validCensusTractRule, CensusTractInputField } from 'apps/patient/data/address';
import { Input } from 'components/FormInputs/Input';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { validZipCodeRule, ZipCodeInputField } from 'libs/demographics/location';
import { useLocationCodedValues } from 'location';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { BasicNewPatientEntry } from '../entry';

const STREET_ADDRESS_LABEL = 'Street address 1';
const STREET_ADDRESS_2_LABEL = 'Street address 2';
const CITY_LABEL = 'City';
const ZIP_LABEL = 'Zip';
const CENSUS_TRACT_LABEL = 'Census tract';

type AddressEntryFieldsProps = EntryFieldsProps;

export const BasicAddressFields = ({ orientation = 'horizontal' }: AddressEntryFieldsProps) => {
    const { control, reset } = useFormContext<BasicNewPatientEntry>();
    const location = useLocationCodedValues();
    const selectedState = useWatch({ control, name: 'address.state' });
    const enteredCity = useWatch({ control, name: 'address.city' });
    const enteredZip = useWatch({ control, name: 'address.zipcode' });
    const counties = location.counties.byState(selectedState?.value ?? '');

    const handleSuggestionSelection = (selected: AddressSuggestion) => {
        reset(
            {
                address: {
                    address1: selected.address1,
                    city: selected.city,
                    state: selected.state ?? undefined,
                    zipcode: selected.zip
                }
            },
            { keepDefaultValues: true }
        );
    };

    return (
        <section>
            <Controller
                control={control}
                name="address.address1"
                rules={maxLengthRule(100, STREET_ADDRESS_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <AddressSuggestionInput
                        label={STREET_ADDRESS_LABEL}
                        orientation={orientation}
                        sizing="compact"
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
                name="address.address2"
                rules={maxLengthRule(100, STREET_ADDRESS_2_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={STREET_ADDRESS_2_LABEL}
                        orientation={orientation}
                        sizing="compact"
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
                name="address.city"
                rules={maxLengthRule(100, CITY_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={CITY_LABEL}
                        orientation={orientation}
                        sizing="compact"
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
                name="address.state"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label="State"
                        orientation={orientation}
                        sizing="compact"
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
                        sizing="compact"
                        error={error?.message}
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
                        sizing="compact"
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
                        sizing="compact"
                        error={error?.message}
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
                        sizing="compact"
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={location.countries}
                        autoComplete="off"
                    />
                )}
            />
        </section>
    );
};
