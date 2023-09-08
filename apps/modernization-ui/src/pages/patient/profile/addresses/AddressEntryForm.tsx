import { Button, Grid, Icon, Label, ModalFooter, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { externalizeDateTime } from 'date';
import { useLocationCodedValues } from 'location';
import { orNull } from 'utils';
import { usePatientAddressCodedValues } from './usePatientAddressCodedValues';

import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';
import { AddressEntry } from './AddressEntry';

import './AddressEntryForm.scss';

type EntryProps = {
    action: string;
    entry: AddressEntry;
    onChange: (updated: AddressEntry) => void;
    onDelete?: () => void;
};

export const AddressEntryForm = ({ action, entry, onChange, onDelete }: EntryProps) => {
    const {
        handleSubmit,
        control,
        formState: { isValid },
        setValue
    } = useForm({ mode: 'onBlur' });

    const selectedState = useWatch({ control, name: 'state', defaultValue: entry.state });
    const enteredCity = useWatch({ control, name: 'city', defaultValue: entry.city });
    const enteredZip = useWatch({ control, name: 'zipcode', defaultValue: entry.zipcode });

    const coded = usePatientAddressCodedValues();
    const location = useLocationCodedValues();
    const counties = location.counties.byState(selectedState);

    const handleSuggestionSelection = (selected: AddressSuggestion) => {
        setValue('streetAddress1', selected.address1);
        setValue('city', selected.city);
        setValue('state', selected.state?.value);
        setValue('zipcode', selected.zip);
    };

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
                                    required
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
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    htmlFor={'type'}
                                    label="Type"
                                    options={coded.types}
                                    error={error?.message}
                                    required
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
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    htmlFor={'use'}
                                    label="Use"
                                    options={coded.uses}
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="address1"
                            defaultValue={entry.address1}
                            render={({ field: { onChange, value, name } }) => (
                                <AddressSuggestionInput
                                    flexBox
                                    id={name}
                                    locations={location}
                                    criteria={{ city: enteredCity, state: selectedState, zip: enteredZip }}
                                    label="Street address 1"
                                    defaultValue={value}
                                    onChange={onChange}
                                    onSelection={handleSuggestionSelection}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="address2"
                            defaultValue={entry.address2}
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="Street address 2"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="city"
                            defaultValue={entry.city}
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="City"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="state"
                            defaultValue={entry.state}
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={name}
                                    label="State"
                                    options={location.states.all}
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
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="Zip"
                                    name="zipcode"
                                    htmlFor={name}
                                    id={name}
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
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={name}
                                    label="County"
                                    options={counties}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="censusTract"
                            defaultValue={entry.censusTract}
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="Census tract"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="country"
                            defaultValue={entry.country}
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={name}
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
                            render={({ field: { onChange, value, name } }) => (
                                <Grid col>
                                    <Grid className="flex-align-self-center">
                                        <Label htmlFor={name}>Additional comments:</Label>
                                    </Grid>
                                    <Grid>
                                        <Textarea onChange={onChange} name={name} id={name} defaultValue={value} />
                                    </Grid>
                                </Grid>
                            )}
                        />
                    </Grid>
                </Grid>
            </div>
            <ModalFooter className="padding-2 margin-left-auto flex-justify display-flex details-footer">
                <Button
                    unstyled
                    className={`text-red display-flex flex-align-center delete--modal-btn ${
                        action !== 'Edit' ? 'ds-u-visibility--hidden' : ''
                    }`}
                    type="button"
                    onClick={onDelete}>
                    <Icon.Delete className="delete-icon" />
                    Delete
                </Button>

                <Button
                    disabled={!isValid}
                    onClick={handleSubmit(onSubmit)}
                    type="submit"
                    className="padding-105 text-center margin-0">
                    Save
                </Button>
            </ModalFooter>
        </div>
    );
};
