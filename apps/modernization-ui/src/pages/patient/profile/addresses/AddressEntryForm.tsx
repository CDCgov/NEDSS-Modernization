import { Button, ButtonGroup, Grid, Label, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { usePatientAddressCodedValues } from './usePatientAddressCodedValues';
import { useCountyCodedValues, useLocationCodedValues } from 'location';
import { AddressEntry } from './AddressEntry';

type EntryProps = {
    action: string;
    entry: AddressEntry;
    onChange: (updated: AddressEntry) => void;
    onCancel: () => void;
};

export const AddressEntryForm = ({ action, entry, onChange, onCancel }: EntryProps) => {
    const { handleSubmit, control } = useForm();

    const selectedState = useWatch({ control, name: 'state', defaultValue: entry.state });

    const coded = usePatientAddressCodedValues();
    const location = useLocationCodedValues();
    const byState = useCountyCodedValues(selectedState);

    const onSubmit = (entered: FieldValues) => {
        onChange({
            ...entry,
            ...entered
        });
    };

    return (
        <div className="width-full maxw-full modal-form">
            <div className="modal-body">
                <Grid row>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="asOf"
                            rules={{ required: true }}
                            defaultValue={entry.asOf}
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="asOf"
                                    htmlFor={'asOf'}
                                    label="As of"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="type"
                            rules={{ required: true }}
                            defaultValue={entry.type}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'type'}
                                    label="Type"
                                    options={coded.types}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="use"
                            rules={{ required: true }}
                            defaultValue={entry.type}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'use'}
                                    label="Use"
                                    options={coded.uses}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="address1"
                            defaultValue={entry.address1}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Street address 1"
                                    name="address1"
                                    htmlFor="address1"
                                    id="address1"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="address2"
                            defaultValue={entry.address2}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Street address 2"
                                    name="address2"
                                    htmlFor="address2"
                                    id="address2"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="city"
                            defaultValue={entry.city}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="City"
                                    name="city"
                                    htmlFor="city"
                                    id="city"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="state"
                            defaultValue={entry.state}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'state'}
                                    label="State"
                                    options={location.states}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="zipcode"
                            defaultValue={entry.zipcode}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Zip"
                                    name="zipcode"
                                    htmlFor="zipcode"
                                    id="zipcode"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="county"
                            defaultValue={entry.county}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'county'}
                                    label="County"
                                    options={byState.counties}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="censusTract"
                            defaultValue={entry.censusTract}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Census tract"
                                    name="censusTract"
                                    htmlFor="censusTract"
                                    id="censusTract"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="country"
                            defaultValue={entry.country}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'country'}
                                    label="Country"
                                    options={location.countries}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="comment"
                            defaultValue={entry.comment}
                            render={({ field: { onChange, value } }) => (
                                <Grid col>
                                    <Grid className="flex-align-self-center">
                                        <Label htmlFor={'comment'}>Additional comments:</Label>
                                    </Grid>
                                    <Grid>
                                        <Textarea
                                            onChange={onChange}
                                            name="comment"
                                            id="comment"
                                            defaultValue={value}
                                        />
                                    </Grid>
                                </Grid>
                            )}
                        />
                    </Grid>
                </Grid>
            </div>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" data-testid="cancel-btn" outline onClick={onCancel}>
                        Go Back
                    </Button>
                    <Button
                        onClick={handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0">
                        {action}
                    </Button>
                </ButtonGroup>
            </div>
        </div>
    );
};
