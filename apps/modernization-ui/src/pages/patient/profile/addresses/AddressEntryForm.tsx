import { Button, ButtonGroup, Grid, Label, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { externalizeDateTime } from 'date';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { usePatientAddressCodedValues } from './usePatientAddressCodedValues';
import { useCountyCodedValues, useLocationCodedValues } from 'location';
import { AddressEntry } from './AddressEntry';
import { orNull } from 'utils';

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
            asOf: externalizeDateTime(entered.asOf),
            use: orNull(entered.use),
            type: orNull(entered.type),
            address1: entered.address1,
            address2: entered.address2,
            city: entered.city,
            state: orNull(entered.state),
            zipcode: entered.zipcode,
            county: orNull(entered.county),
            censusTract: entered.censusTract,
            country: orNull(entered.country),
            comment: entered.comment
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
                            rules={{ required: { value: true, message: 'As of date is required.' } }}
                            defaultValue={entry.asOf}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <DatePickerInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    name="asOf"
                                    htmlFor={'asOf'}
                                    label="As of"
                                    errorMessage={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="type"
                            defaultValue={entry.type}
                            rules={{ required: { value: true, message: 'Type is required.' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'type'}
                                    label="Type"
                                    options={coded.types}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="use"
                            defaultValue={entry.use}
                            rules={{ required: { value: true, message: 'Use is required.' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'use'}
                                    label="Use"
                                    options={coded.uses}
                                    error={error?.message}
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
                            rules={{
                                pattern: { value: /^\d{5}(?:[-\s]\d{4})?$/, message: 'Invalid zip code' }
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Zip"
                                    name="zipcode"
                                    htmlFor="zipcode"
                                    id="zipcode"
                                    error={error?.message}
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
