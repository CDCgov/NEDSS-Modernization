import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { LocationCodedValues } from 'location';
import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';

import { Grid } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { SelectInput } from 'components/FormInputs/SelectInput';

import { Input } from 'components/FormInputs/Input';

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

    const counties = coded.counties.byState(selectedState);

    function handleSuggestionSelection(selected: AddressSuggestion) {
        setValue('streetAddress1', selected.address1);
        setValue('city', selected.city);
        setValue('state', selected.state?.value);
        setValue('zip', selected.zip);
    }

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3 address-fields-grid">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="streetAddress1"
                            render={({ field: { onChange, value, name } }) => (
                                <AddressSuggestionInput
                                    id={name}
                                    locations={coded}
                                    criteria={{ zip: enteredZip, city: enteredCity, state: selectedState }}
                                    label="Street address 1"
                                    defaultValue={value}
                                    onChange={onChange}
                                    onSelection={handleSuggestionSelection}
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
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Street address 2"
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
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
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    id={name}
                                    name={name}
                                    type="text"
                                    label="City"
                                    htmlFor={name}
                                    defaultValue={value}
                                    onChange={onChange}
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
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name={name}
                                    htmlFor={name}
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
                            rules={{ pattern: { value: /[\d]{5}(-[\d]{4})?/, message: 'Invalid zip' } }}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    id={name}
                                    name={name}
                                    type="text"
                                    label="ZIP"
                                    htmlFor={name}
                                    defaultValue={value}
                                    error={error?.message}
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
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name={name}
                                    htmlFor={name}
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
                            rules={{
                                pattern: {
                                    value: /[0-9]{4}(.(([0-8][0-9])|([9][0-8])))?/,
                                    message:
                                        ' Census Tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.'
                                }
                            }}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Census Tract"
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
                            name="country"
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name={name}
                                    htmlFor={name}
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
