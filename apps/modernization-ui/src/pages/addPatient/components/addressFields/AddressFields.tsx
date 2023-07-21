import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { ErrorMessage, Grid, Label, TextInput } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { SelectInput } from 'components/FormInputs/SelectInput';

import { CodedValue } from 'coded';
import { Input } from 'components/FormInputs/Input';
import { StateCodedValue } from 'location';

type CodedValueLists = {
    states: StateCodedValue[];
    countries: CodedValue[];
    byState: (state: string) => CodedValue[];
};

type Props = {
    id: string;
    title: string;
    coded: CodedValueLists;
};

export default function AddressFields({ id, title, coded }: Props) {
    const { control } = useFormContext();

    const selectedState = useWatch({ control, name: 'state' });

    const counties = coded.byState(selectedState);

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="streetAddress1"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Street address 1"
                                    defaultValue={value}
                                    htmlFor="streetAddress1"
                                    id="streetAddress1"
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
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Street address 2"
                                    defaultValue={value}
                                    htmlFor="streetAddress2"
                                    id="streetAddress2"
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
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    id="city"
                                    name="city"
                                    type="text"
                                    label="City"
                                    htmlFor="city"
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
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name="state"
                                    htmlFor={'state'}
                                    label="State"
                                    options={coded.states}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={2}>
                        <Controller
                            control={control}
                            name="zip"
                            rules={{ pattern: { value: /[\d]{5}(-[\d]{4})?/, message: 'Invalid zip' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <>
                                    <Label htmlFor="zip">ZIP</Label>
                                    <TextInput
                                        id="zip"
                                        name="zip"
                                        type="text"
                                        inputSize="medium"
                                        defaultValue={value}
                                        onChange={onChange}
                                    />
                                    {error && <ErrorMessage>{error.message}</ErrorMessage>}
                                </>
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="county"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name="county"
                                    htmlFor={'county'}
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
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Census Tract"
                                    defaultValue={value}
                                    htmlFor="censusTract"
                                    id="censusTract"
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
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name="country"
                                    htmlFor={'country'}
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
