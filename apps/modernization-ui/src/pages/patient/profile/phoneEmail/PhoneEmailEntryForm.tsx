import { Button, Grid, Icon, Label, ModalFooter, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { usePatientPhoneCodedValues } from './usePatientPhoneCodedValues';
import { PhoneEmailEntry } from './PhoneEmailEntry';
import { validatePhoneNumber } from 'validation/phone';

type EntryProps = {
    action: string;
    entry: PhoneEmailEntry;
    onChange: (updated: PhoneEmailEntry) => void;
    onDelete?: () => void;
};

export const PhoneEmailEntryForm = ({ action, entry, onChange, onDelete }: EntryProps) => {
    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm({ mode: 'onBlur' });

    const coded = usePatientPhoneCodedValues();

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
                            defaultValue={entry.asOf}
                            rules={{ required: { value: true, message: 'As of date is required.' } }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <DatePickerInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    name="asOf"
                                    disableFutureDates
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
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
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
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
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
                            name="countryCode"
                            defaultValue={entry.countryCode}
                            rules={{
                                pattern: {
                                    value: /^\+?\d{1,3}$/,
                                    message: 'A country code should be 1 to 3 digits'
                                }
                            }}
                            render={({ field: { onChange, value, onBlur }, fieldState: { error } }) => {
                                return (
                                    <Input
                                        flexBox
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        defaultValue={value}
                                        type="tel"
                                        label="Country code"
                                        name="countryCode"
                                        htmlFor="countryCode"
                                        id="countryCode"
                                        error={error?.message}
                                    />
                                );
                            }}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="number"
                            defaultValue={entry.number}
                            rules={{
                                validate: {
                                    properNumber: (value) => validatePhoneNumber(value)
                                }
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Phone number"
                                    name="number"
                                    htmlFor="number"
                                    id="number"
                                    mask="___-___-____"
                                    pattern="\d{3}-\d{3}-\d{4}"
                                    error={
                                        error &&
                                        'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
                                    }
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="extension"
                            defaultValue={entry.extension}
                            rules={{
                                pattern: {
                                    value: /^\+?\d{1,4}$/,
                                    message: 'A Extension should be 1 to 4 digits'
                                }
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Extension"
                                    name="extension"
                                    htmlFor="extension"
                                    id="extension"
                                    error={error && 'Invalid extension'}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="email"
                            defaultValue={entry.email}
                            rules={{
                                pattern: {
                                    value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                                    message: 'Please enter a valid email address (example: youremail@website.com)'
                                }
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Email address"
                                    name="email"
                                    htmlFor="email"
                                    id="email"
                                    error={
                                        error && 'Please enter a valid email address (example: youremail@website.com)'
                                    }
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="url"
                            defaultValue={entry.url}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Url"
                                    name="url"
                                    htmlFor="url"
                                    id="url"
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
                                <Grid>
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
