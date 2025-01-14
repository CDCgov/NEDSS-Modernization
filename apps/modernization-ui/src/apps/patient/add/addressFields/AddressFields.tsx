import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { LocationCodedValues } from 'location';
import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';

import { Grid } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';

import { Input } from 'components/FormInputs/Input';
import { maxLengthRule } from 'validation/entry';
import { SingleSelect } from 'design-system/select';
import { validZipCodeRule, ZipCodeInputField } from 'libs/demographics/location';
import { CensusTractInputField, validCensusTractRule } from 'apps/patient/data/address';

const CENSUS_TRACT_LABEL = 'Census tract';

type Props = {
    id: string;
    title: string;
    coded: LocationCodedValues;
};

export default function AddressFields({ id, title, coded }: Props) {
    const { control, setValue } = useFormContext();

    const selectedState = useWatch({ control, name: 'state' });
    const enteredCity = useWatch({ control, name: 'city' });
    const enteredZip = useWatch({ control, name: 'zip' });

    const counties = coded.counties.byState(selectedState?.value);

    const handleSuggestionSelection = (selected: AddressSuggestion) => {
        setValue('streetAddress1', selected.address1);
        setValue('city', selected.city);
        setValue('state', selected.state?.value);
        setValue('zip', selected.zip);
    };

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3 address-fields-grid">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="streetAddress1"
                            rules={maxLengthRule(100)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <AddressSuggestionInput
                                    id={name}
                                    onBlur={onBlur}
                                    locations={coded}
                                    criteria={{ zip: enteredZip, city: enteredCity, state: selectedState }}
                                    label="Street address 1"
                                    value={value}
                                    onChange={onChange}
                                    onSelection={handleSuggestionSelection}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="streetAddress2"
                            rules={maxLengthRule(100)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    type="text"
                                    label="Street address 2"
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="city"
                            rules={maxLengthRule(100)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    id={name}
                                    name={name}
                                    type="text"
                                    label="City"
                                    htmlFor={name}
                                    defaultValue={value}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row gap={2}>
                    <Grid col={4}>
                        <Controller
                            control={control}
                            name="state"
                            render={({ field: { onChange, value, name } }) => (
                                <SingleSelect
                                    id={name}
                                    onChange={onChange}
                                    value={value}
                                    name={name}
                                    label="State"
                                    options={coded.states.all}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={2}>
                        <Controller
                            control={control}
                            name="zip"
                            rules={validZipCodeRule('ZIP code')}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <ZipCodeInputField
                                    id={name}
                                    name={name}
                                    label="ZIP"
                                    orientation="vertical"
                                    value={value}
                                    error={error?.message}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="county"
                            render={({ field: { onChange, value, name } }) => (
                                <SingleSelect
                                    id={name}
                                    onChange={onChange}
                                    value={value}
                                    name={name}
                                    label="County"
                                    options={counties}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
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
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="country"
                            render={({ field: { onChange, value, name } }) => (
                                <SingleSelect
                                    id={name}
                                    onChange={onChange}
                                    value={value}
                                    name={name}
                                    label="Country"
                                    options={coded.countries}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
