import { ErrorMessage, Grid, Label, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { externalizeDateTime } from 'date';
import { useLocationCodedValues } from 'location';
import { orNull } from 'utils';
import { EntryFooter } from 'apps/patient/profile/entry';
import { usePatientAddressCodedValues } from './usePatientAddressCodedValues';

import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';

import { maxLengthRule } from 'validation/entry';
import { AddressEntry } from './AddressEntry';
import './AddressEntryForm.scss';

type EntryProps = {
    entry: AddressEntry;
    onChange: (updated: AddressEntry) => void;
    onDelete?: () => void;
};

export const AddressEntryForm = ({ entry, onChange, onDelete }: EntryProps) => {
    const { handleSubmit, control, setValue } = useForm({ mode: 'onBlur' });

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
        <>
            <section>
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
                                    disableFutureDates
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
                            rules={maxLengthRule(100)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <AddressSuggestionInput
                                    flexBox
                                    id={name}
                                    locations={location}
                                    criteria={{ city: enteredCity, state: selectedState, zip: enteredZip }}
                                    label="Street address 1"
                                    defaultValue={value}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    onSelection={handleSuggestionSelection}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="address2"
                            defaultValue={entry.address2}
                            rules={maxLengthRule(100)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    defaultValue={value}
                                    type="text"
                                    label="Street address 2"
                                    name={name}
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
                            name="city"
                            defaultValue={entry.city}
                            rules={maxLengthRule(100)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    defaultValue={value}
                                    type="text"
                                    label="City"
                                    name={name}
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
                                pattern: {
                                    value: /^\d{5}(?:[-\s]\d{4})?$/,
                                    message:
                                        'Please enter a valid ZIP code (XXXXX) using only numeric characters (0-9).'
                                },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, value, name, onBlur }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    defaultValue={value}
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
                            rules={maxLengthRule(10)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    defaultValue={value}
                                    type="text"
                                    label="Census tract"
                                    name={name}
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
                            rules={maxLengthRule(2000)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Grid col>
                                    <Grid className="flex-align-self-center">
                                        <Label htmlFor={name}>Additional comments:</Label>
                                    </Grid>
                                    <Grid>
                                        <Textarea
                                            onChange={onChange}
                                            onBlur={onBlur}
                                            name={name}
                                            id={name}
                                            defaultValue={value}
                                        />
                                        {error?.message && (
                                            <ErrorMessage id={error.message}>{error.message}</ErrorMessage>
                                        )}
                                    </Grid>
                                </Grid>
                            )}
                        />
                    </Grid>
                </Grid>
            </section>
            <EntryFooter onSave={handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
