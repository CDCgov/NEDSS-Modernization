import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { useLocationCodedValues } from 'location';
import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';
import { validCensusTractRule, CensusTractInputField } from 'apps/patient/data/address';
import { Input } from 'components/FormInputs/Input';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { validZipCodeRule, ZipCodeInputField } from 'libs/demographics/location';
import { maxLengthRule } from 'validation/entry';
import { BasicNewPatientEntry } from 'apps/patient/add/basic/entry';
import { useCountryCodedValues } from 'apps/patient/data/country/useCountryCodedValues';
import { useStateCodedValues } from 'apps/patient/data/state/useStateCodedValues';
import { useEffect, useState } from 'react';
import { CountyOptionsService } from 'generated';
import { Option } from 'generated';

const STREET_ADDRESS_LABEL = 'Street address 1';
const STREET_ADDRESS_2_LABEL = 'Street address 2';
const CITY_LABEL = 'City';
const ZIP_LABEL = 'Zip';
const CENSUS_TRACT_LABEL = 'Census tract';

export const BasicAddressFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control, reset } = useFormContext<BasicNewPatientEntry>();
    const selectedState = useWatch({ control, name: 'address.state' });
    const enteredCity = useWatch({ control, name: 'address.city' });
    const enteredZip = useWatch({ control, name: 'address.zipcode' });
    const location = useLocationCodedValues();
    const [counties, setCounties] = useState<Option[]>([]);

    useEffect(() => {
        (selectedState?.value ?? '').length == 0
            ? []
            : CountyOptionsService.countyAutocomplete({
                  criteria: '',
                  state: selectedState!.value,
                  limit: 100000
              }).then((response) => {
                  setCounties(response);
              });
    }, [selectedState]);

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
                        sizing={sizing}
                        id={name}
                        locations={location}
                        criteria={{
                            city: enteredCity ?? undefined,
                            state: selectedState?.value ?? undefined,
                            zip: enteredZip ?? undefined
                        }}
                        value={value}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={useStateCodedValues({ lazy: false }).options}
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
                        options={useCountryCodedValues({ lazy: false }).options}
                        autoComplete="off"
                    />
                )}
            />
        </section>
    );
};
